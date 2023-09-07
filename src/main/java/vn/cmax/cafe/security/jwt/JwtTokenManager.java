package vn.cmax.cafe.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import vn.cmax.cafe.configuration.model.SecurityProperties;
import vn.cmax.cafe.exception.CmaxException;
import vn.cmax.cafe.exception.TechnicalException;
import vn.cmax.cafe.security.jwt.model.Token;
import vn.cmax.cafe.user.UserEntity;
import vn.cmax.cafe.user.UserRepository;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenManager {
  public static final String BEARER_KEY = "Bearer ";
  public static final String USER_FINGERPRINT_CLAIMS_KEY = "userFingerprint";
  private final SecureRandom secureRandom = new SecureRandom();
  private Key secretKey;

  private final JwtTokenService userTokenStore;
  private final SecurityProperties securityProperties;
  private final UserRepository userRepository;

  public JwtTokenManager(
      JwtTokenService userTokenStore,
      SecurityProperties securityProperties,
      UserRepository userRepository) {
    this.userTokenStore = userTokenStore;
    this.securityProperties = securityProperties;
    this.userRepository = userRepository;
  }

  @PostConstruct
  public void init() {
    this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
  }

  public Token createToken(UserEntity userEntity, TokenType tokenType) throws CmaxException {
    Date now = Calendar.getInstance().getTime();
    String jwId = UUID.randomUUID().toString();

    String fingerPrint = generateFingerprint();
    String fingerPrintHash;
    try {
      fingerPrintHash = computeFingerPrintHash(fingerPrint);
    } catch (NoSuchAlgorithmException ex) {
      log.error("computeFingerPrintHash has exception", ex);
      throw new TechnicalException();
    }
    String subject = userEntity.getId().toString();
    Claims claims = Jwts.claims();
    claims.setSubject(subject);
    claims.setId(jwId);
    claims.setIssuedAt(now);
    claims.setExpiration(getValidity(now, tokenType));
    claims.setIssuer(this.securityProperties.getJwt().getIssuer());
    claims.put(USER_FINGERPRINT_CLAIMS_KEY, fingerPrintHash);

    String jwtToken = Jwts.builder().setClaims(claims).signWith(secretKey).compact();
    return new Token(jwtToken, fingerPrint);
  }

  public String getUserId(String token) {
    return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
  }

  public String resolveToken(HttpServletRequest req) {
    String bearerToken = req.getHeader(HttpHeaders.AUTHORIZATION);
    if (bearerToken != null && bearerToken.startsWith(BEARER_KEY)) {
      return bearerToken.substring(BEARER_KEY.length());
    }
    return null;
  }

  public JwtAuthentication authenticate(
      String token, TokenType jwtTokenType, HttpServletRequest request)
      throws NoSuchAlgorithmException {
    // step 1: check if finger print is in cookie
    String fingerprintFromCookie = getFingerprintFromCookie(request, jwtTokenType);
    if (fingerprintFromCookie == null) {
      return JwtAuthentication.builder()
          .success(false)
          .message("Fingerprint wasn't set into cookie request header")
          .build();
    }

    // step 2: parse claim, if cannot parse meaning token is invalid or expired
    Jws<Claims> claims;
    try {
      claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
    } catch (ExpiredJwtException e) {
      String errorMessage =
          MessageFormat.format(
              "[{0}] {1} has expired",
              this.getClass().getCanonicalName(), jwtTokenType + " has expired");
      log.warn(errorMessage, e);
      return JwtAuthentication.builder().success(false).message(errorMessage).build();
    } catch (JwtException | IllegalArgumentException | UsernameNotFoundException e1) {
      String errorMessage =
          MessageFormat.format(
              "[{0}] Exception happens when authenticate jwt token",
              this.getClass().getCanonicalName());
      log.warn(errorMessage, e1);
      return JwtAuthentication.builder().success(false).message(errorMessage).build();
    }

    String subject = claims.getBody().getSubject();
    // step 3: verify fingerprint is valid
    Optional<UserEntity> userEntityOpt = this.userRepository.findById(Long.valueOf(subject));
    if (userEntityOpt.isEmpty()) {
      log.error(
          "[{0}] Could not find user is associated to token claims [subject = {1}]",
          this.getClass().getSimpleName(), subject);
      return JwtAuthentication.builder().success(false).message("User could not be found").build();
    }
    UserEntity userEntity = userEntityOpt.get();
    String fingerprintHash = computeFingerPrintHash(fingerprintFromCookie);
    String fingerprint = (String) claims.getBody().get(USER_FINGERPRINT_CLAIMS_KEY);
    if (fingerprintHash.equals(fingerprint) == Boolean.FALSE) {
      String authenticationMessage =
          MessageFormat.format(
              "[{0}] Fingerprint does not match for user {1}",
              this.getClass().getCanonicalName(), userEntity.getId());
      log.warn(authenticationMessage);
      return JwtAuthentication.builder().success(false).message(authenticationMessage).build();
    }

    // step 4: verify access token or refresh token
    boolean tokenMatch;
    if (jwtTokenType == TokenType.ACCESS_TOKEN) {
      tokenMatch = userTokenStore.validateUserAccessToken(userEntity.getId(), token);
    } else {
      tokenMatch = userTokenStore.validateUserRefreshToken(userEntity.getId(), token);
    }
    if (tokenMatch == Boolean.FALSE) {
      String authenticationMessage = "Token does not match for user: " + userEntity.getEmail();
      log.warn(authenticationMessage);
      return JwtAuthentication.builder().success(false).message(authenticationMessage).build();
    }

    Authentication authentication =
        new UsernamePasswordAuthenticationToken(userEntity, "", userEntity.getAuthorities());
    String authenticationMessage = "Valid jwt token";
    return JwtAuthentication.builder()
        .success(true)
        .message(authenticationMessage)
        .authentication(authentication)
        .build();
  }

  private String getFingerprintFromCookie(HttpServletRequest request, TokenType tokenType) {
    String userFingerprint = null;
    if (request.getCookies() != null && request.getCookies().length > 0) {
      List<Cookie> cookies = Arrays.stream(request.getCookies()).collect(Collectors.toList());
      Optional<Cookie> cookie =
          cookies.stream().filter(c -> tokenType.toString().equals(c.getName())).findFirst();
      if (cookie.isPresent()) {
        userFingerprint = cookie.get().getValue();
      }
    }
    return userFingerprint;
  }

  private Date getValidity(Date currentDate, TokenType tokenType) {
    if (tokenType == TokenType.ACCESS_TOKEN) {
      return new Date(
          currentDate.getTime() + this.securityProperties.getJwt().getAccessTokenTimeout());
    }
    return new Date(
        currentDate.getTime() + this.securityProperties.getJwt().getRefreshTokenTimeout());
  }

  private String generateFingerprint() {
    byte[] randomFingerPrint = new byte[50];
    this.secureRandom.nextBytes(randomFingerPrint);
    return DatatypeConverter.printHexBinary(randomFingerPrint);
  }

  private String computeFingerPrintHash(String fingerPrint) throws NoSuchAlgorithmException {
    MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
    byte[] fingerPrintDigest = messageDigest.digest(fingerPrint.getBytes(StandardCharsets.UTF_8));
    return DatatypeConverter.printHexBinary(fingerPrintDigest);
  }
}
