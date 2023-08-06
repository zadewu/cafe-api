package vn.cmax.cafe.support;

import org.apache.catalina.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import vn.cmax.cafe.user.UserEntity;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<Long> {

  @Override
  public Optional<Long> getCurrentAuditor() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null) {
      return Optional.of(0L);
    }
    Object principal = authentication.getPrincipal();
    if (principal != null && principal instanceof UserEntity) {
      UserEntity userEntity = (UserEntity) principal;
      return Optional.of(userEntity.getId());
    }
    return Optional.of(0L);
  }
}
