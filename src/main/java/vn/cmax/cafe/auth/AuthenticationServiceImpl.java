package vn.cmax.cafe.auth;

import java.util.Optional;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.cmax.cafe.api.models.AuthenticationRequest;
import vn.cmax.cafe.api.models.AuthenticationResponse;
import vn.cmax.cafe.api.models.RefreshTokenResponse;
import vn.cmax.cafe.configuration.model.SecurityProperties;
import vn.cmax.cafe.exception.ApiErrorMessages;
import vn.cmax.cafe.exception.CmaxException;
import vn.cmax.cafe.exception.ForbiddenException;
import vn.cmax.cafe.security.jwt.JwtTokenManager;
import vn.cmax.cafe.security.jwt.JwtTokenService;
import vn.cmax.cafe.security.jwt.TokenType;
import vn.cmax.cafe.security.jwt.model.CacheToken;
import vn.cmax.cafe.security.jwt.model.Token;
import vn.cmax.cafe.user.UserEntity;
import vn.cmax.cafe.user.UserRepository;
import vn.cmax.cafe.user.UserRole;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AuthenticationServiceImpl implements AuthenticationService {
  private static final String DEFAULT_COOKIE_PATH = "/";

  private JwtTokenManager jwtTokenManager;

  private JwtTokenService jwtTokenService;

  private SecurityProperties securityProperties;

  private UserRepository userRepository;
  private PasswordEncoder passwordEncoder;

  @Override
  public AuthenticationResponse login(
      AuthenticationRequest authenticationRequest, HttpServletResponse response)
      throws CmaxException {
    Optional<UserEntity> userEntityOptional =
        this.userRepository.findByUsername(authenticationRequest.getUsername());
    if (userEntityOptional.isEmpty()) {
      throw new ForbiddenException(ApiErrorMessages.INVALID_USERNAME_PASSWORD_ERR_MESSAGE);
    }
    UserEntity userEntity = userEntityOptional.get();
    String userPassword = userEntity.getPassword();
    if (!passwordEncoder.matches(authenticationRequest.getPassword(), userPassword)) {
      throw new ForbiddenException(ApiErrorMessages.INVALID_USERNAME_PASSWORD_ERR_MESSAGE);
    }
    CacheToken token = setJwtTokenForUser(userEntity, response);
    AuthenticationResponse authenticationResponse = new AuthenticationResponse();
    authenticationResponse
        .username(userEntity.getUsername())
        .accessToken(token.getAccessToken())
        .refreshToken(token.getRefreshToken());
    return authenticationResponse;
  }

  @Override
  public UserEntity getCurrentAuthenticatedUser() throws CmaxException {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null) {
      log.warn(ApiErrorMessages.MISSING_AUTHENTICATED_USER);
      throw new ForbiddenException(ApiErrorMessages.MISSING_AUTHENTICATED_USER);
    }

    Object principal = authentication.getPrincipal();
    if (principal == null || !(principal instanceof UserEntity)) {
      log.warn(ApiErrorMessages.PRINCIPAL_IS_NOT_A_USER_ENTITY);
      return null;
    }
    return (UserEntity) principal;
  }

  @Override
  public boolean isSuperUser(UserEntity userEntity) {
    return userEntity.getRoles().stream().anyMatch(role -> role == UserRole.ADMIN);
  }

  @Override
  public void logout(HttpServletRequest request, HttpServletResponse response)
      throws CmaxException {
    String accessToken = this.jwtTokenManager.resolveToken(request);
    UserEntity user = this.getCurrentAuthenticatedUser();
    SecurityContextHolder.getContext().setAuthentication(null);
    SecurityContextHolder.clearContext();
    this.jwtTokenService.clearTokenOfUser(user.getId(), accessToken);
    cleanCookies(request, response);
  }

  @Override
  public RefreshTokenResponse refreshToken(HttpServletRequest request, HttpServletResponse response)
      throws CmaxException {
    String refreshToken = this.jwtTokenManager.resolveToken(request);
    final String userId = jwtTokenManager.getUserId(refreshToken);
    Optional<UserEntity> userEntityOpt = this.userRepository.findById(Long.valueOf(userId));
    if (userEntityOpt.isEmpty()) {
      throw new ForbiddenException(ApiErrorMessages.MISSING_AUTHENTICATED_USER);
    }
    UserEntity userEntity = userEntityOpt.get();
    Token accessToken = this.jwtTokenManager.createToken(userEntity, TokenType.ACCESS_TOKEN);
    String jwtAccessToken = accessToken.getJwtToken();
    this.jwtTokenService.updateAccessTokenForUser(userEntity.getId(), jwtAccessToken, refreshToken);
    setFingerprintIntoCookie(response, accessToken.getFingerprintToken(), TokenType.ACCESS_TOKEN);
    return new RefreshTokenResponse().accessToken(jwtAccessToken);
  }

  private CacheToken setJwtTokenForUser(UserEntity user, HttpServletResponse response)
      throws CmaxException {
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

  private void cleanCookies(HttpServletRequest request, HttpServletResponse response) {
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals(TokenType.ACCESS_TOKEN.toString())
            || cookie.getName().equals(TokenType.REFRESH_TOKEN.toString())) {
          cookie.setValue("");
          cookie.setPath(DEFAULT_COOKIE_PATH);
          cookie.setMaxAge(0);
          response.addCookie(cookie);
        }
      }
    }
  }

  private void setFingerprintIntoCookie(
      HttpServletResponse response, String fingerprint, TokenType tokenType) {
    String cookiePath =
        this.securityProperties.getCookiePath() == null
            ? DEFAULT_COOKIE_PATH
            : this.securityProperties.getCookiePath();
    String cookieSamesite =
        this.securityProperties.getCookieSamesite() == null
            ? "Lax"
            : this.securityProperties.getCookieSamesite();
    boolean cookieSecure = this.securityProperties.isCookieSecure();
    final ResponseCookie responseCookie =
        ResponseCookie.from(tokenType.toString(), fingerprint)
            .secure(cookieSecure)
            .path(cookiePath)
            .httpOnly(true)
            .sameSite(cookieSamesite)
            .build();
    response.addHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());
  }
}
