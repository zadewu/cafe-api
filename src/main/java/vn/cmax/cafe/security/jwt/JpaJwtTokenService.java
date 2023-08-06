package vn.cmax.cafe.security.jwt;

import java.util.Optional;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import vn.cmax.cafe.configuration.model.SecurityProperties;
import vn.cmax.cafe.security.jwt.model.CacheToken;
import vn.cmax.cafe.user.UserEntity;
import vn.cmax.cafe.user.UserRepository;

@Component
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class JpaJwtTokenService implements JwtTokenService {
  private SecurityProperties securityProperties;
  private UserRepository userRepository;

  @Transactional
  @Override
  public void updateAccessTokenForUser(Long userId, String newAccessToken, String refreshToken) {
    Optional<UserEntity> userEntityOptional = this.userRepository.findById(userId);
    if (userEntityOptional.isPresent()) {
      UserEntity userEntity = userEntityOptional.get();
      Optional<JwtTokenEntity> jwtTokenEntityOpt =
          userEntity.getJwtTokens().stream()
              .filter(jwtToken -> jwtToken.getRefreshToken().equals(refreshToken))
              .findFirst();
      if (jwtTokenEntityOpt.isPresent()) {
        jwtTokenEntityOpt.get().setAccessToken(newAccessToken);
        log.info(
            "[{0}] Grant new access token for user {1}",
            this.getClass().getCanonicalName(), userEntity.getId());
      }
    }
  }

  @Transactional
  @Override
  public void clearTokenOfUser(Long userId, String accessToken) {
    Optional<UserEntity> userEntityOptional = this.userRepository.findById(userId);
    if (userEntityOptional.isPresent()) {
      UserEntity userEntity = userEntityOptional.get();
      Optional<JwtTokenEntity> jwtTokenEntityOptional =
          userEntity.getJwtTokens().stream()
              .filter(jwtTokenEntity -> jwtTokenEntity.getAccessToken().equals(accessToken))
              .findFirst();
      if (jwtTokenEntityOptional.isPresent()) {
        userEntity.removeToken(jwtTokenEntityOptional.get());
        log.info(
            "[{0}] Clear token for user {1}",
            this.getClass().getCanonicalName(), userEntity.getId());
      }
    }
  }

  @Transactional
  @Override
  public void addTokenToUser(Long userId, CacheToken token) {
    Optional<UserEntity> userEntityOptional = this.userRepository.findById(userId);
    if (userEntityOptional.isPresent()) {
      UserEntity userEntity = userEntityOptional.get();
      JwtTokenEntity newToken =
          JwtTokenEntity.builder()
              .accessToken(token.getAccessToken())
              .refreshToken(token.getRefreshToken())
              .build();
      userEntity.addToken(newToken);
      if (userEntity.getJwtTokens().size() > this.securityProperties.getJwt().getMaxTokenPerUser()) {
        JwtTokenEntity oldestToken = userEntity.getJwtTokens().get(0);
        userEntity.removeToken(oldestToken);
        log.info(
                "[{0}] User {1} reach maximum token. Remove oldest token.",
                this.getClass().getCanonicalName(), userEntity.getId());
      }
    }
  }

  @Override
  public boolean validateUserAccessToken(Long userId, String accessToken) {
    Optional<UserEntity> userEntityOptional = this.userRepository.findById(userId);
    if (userEntityOptional.isPresent()) {
      UserEntity userEntity = userEntityOptional.get();
      return userEntity.getJwtTokens().stream()
          .anyMatch(token -> token.getAccessToken().equals(accessToken));
    }
    return false;
  }

  @Override
  public boolean validateUserRefreshToken(Long userId, String refreshToken) {
    Optional<UserEntity> userEntityOptional = this.userRepository.findById(userId);
    if (userEntityOptional.isPresent()) {
      UserEntity userEntity = userEntityOptional.get();
      return userEntity.getJwtTokens().stream()
          .anyMatch(token -> token.getRefreshToken().equals(refreshToken));
    }
    return false;
  }

  @Override
  public int numberTokensOfUser(Long userId) {
    return this.userRepository.findById(userId).map(user -> user.getJwtTokens().size()).orElse(0);
  }
}
