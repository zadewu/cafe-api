package vn.cmax.cafe.auth;

import java.security.NoSuchAlgorithmException;
import java.util.NoSuchElementException;
import java.util.Optional;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.cmax.cafe.api.models.AuthenticationRequest;
import vn.cmax.cafe.api.models.AuthenticationResponse;
import vn.cmax.cafe.configuration.model.SecurityProperties;
import vn.cmax.cafe.exception.CmaxSecurityException;
import vn.cmax.cafe.security.jwt.JwtTokenManager;
import vn.cmax.cafe.security.jwt.JwtTokenService;
import vn.cmax.cafe.security.jwt.TokenType;
import vn.cmax.cafe.security.jwt.model.CacheToken;
import vn.cmax.cafe.security.jwt.model.Token;
import vn.cmax.cafe.user.UserEntity;
import vn.cmax.cafe.user.UserRepository;
import vn.cmax.cafe.user.UserService;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AuthenticationServiceImpl implements AuthenticationService {
  private static final String DEFAULT_COOKIE_PATH = "/";

  private JwtTokenManager jwtTokenManager;

  private JwtTokenService jwtTokenService;

  private SecurityProperties securityProperties;

  private UserRepository userRepository;

  @Override
  public AuthenticationResponse login(
      AuthenticationRequest authenticationRequest, HttpServletResponse response)
      throws NoSuchAlgorithmException {
    Optional<UserEntity> userEntityOptional =
        this.userRepository.findByUsername(authenticationRequest.getUsername());
    if (userEntityOptional.isEmpty()) {
      throw new NoSuchElementException("No user found");
    }
    UserEntity userEntity = userEntityOptional.get();
    CacheToken token = setJwtTokenForUser(userEntity, response);
    AuthenticationResponse authenticationResponse = new AuthenticationResponse();
    authenticationResponse.username(userEntity.getUsername())
            .accessToken(token.getAccessToken())
            .refreshToken(token.getRefreshToken());
    return authenticationResponse;
  }

  @Override
  public UserEntity getCurrentAuthenticatedUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null) {
      log.warn(CmaxSecurityException.MISSING_AUTHENTICATED_USER);
      throw new CmaxSecurityException(
              CmaxSecurityException.MISSING_AUTHENTICATED_USER, HttpStatus.UNAUTHORIZED);
    }

    Object principal = authentication.getPrincipal();
    if (principal == null || !(principal instanceof UserEntity)) {
      log.warn(CmaxSecurityException.PRINCIPAL_IS_NOT_A_USER_ENTITY);
      return null;
    }
    return (UserEntity) principal;
  }

  private CacheToken setJwtTokenForUser(UserEntity user, HttpServletResponse response)
      throws NoSuchAlgorithmException {
    Token accessToken = jwtTokenManager.createToken(user, TokenType.ACCESS_TOKEN);
    String jwtAccessToken = accessToken.getJwtToken();
    setFingerprintIntoCookie(response, accessToken.getFingerprintToken(), TokenType.ACCESS_TOKEN);
    Token refreshToken = jwtTokenManager.createToken(user, TokenType.REFRESH_TOKEN);
    String jwtRefreshToken = refreshToken.getJwtToken();
    setFingerprintIntoCookie(response, refreshToken.getFingerprintToken(), TokenType.REFRESH_TOKEN);
    // cache token of user
    CacheToken token = new CacheToken(jwtAccessToken, jwtRefreshToken);
    jwtTokenService.addTokenToUser(user.getId(), token);
    return token;
  }

  private void setFingerprintIntoCookie(
      HttpServletResponse response, String fingerprint, TokenType tokenType) {
    String cookiePath =
        this.securityProperties.getCookiePath() == null
            ? DEFAULT_COOKIE_PATH
            : this.securityProperties.getCookiePath();
    boolean cookieSecure = this.securityProperties.isCookieSecure();

    Cookie cookie = new Cookie(tokenType.toString(), fingerprint);
    cookie.setHttpOnly(true);
    cookie.setSecure(cookieSecure);
    cookie.setPath(cookiePath);
    response.addCookie(cookie);
  }
}
