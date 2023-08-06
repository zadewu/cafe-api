package vn.cmax.cafe.security.jwt;

import vn.cmax.cafe.security.jwt.model.CacheToken;

public interface JwtTokenService {
  void updateAccessTokenForUser(Long userId, String newAccessToken, String refreshToken);

  void clearTokenOfUser(Long userId, String accessToken);

  void addTokenToUser(Long userId, CacheToken token);

  boolean validateUserAccessToken(Long userId, String accessToken);

  boolean validateUserRefreshToken(Long userId, String refreshToken);

  int numberTokensOfUser(Long userId);
}
