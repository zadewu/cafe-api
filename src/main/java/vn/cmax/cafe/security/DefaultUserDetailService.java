package vn.cmax.cafe.security;

import java.util.stream.Collectors;
import org.springframework.security.core.userdetails.*;
import vn.cmax.cafe.user.UserEntity;
import vn.cmax.cafe.user.UserService;

public class DefaultUserDetailService implements UserDetailsService, UserDetailsPasswordService {
  private final UserService userService;

  public DefaultUserDetailService(UserService userService) {
    this.userService = userService;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserEntity userEntity = this.userService.getUser(username);
    return User.builder()
        .username(userEntity.getUsername())
        .password(userEntity.getPassword())
        .roles(
            userEntity.getRoles().stream()
                .map(role -> role.name())
                .collect(Collectors.toList())
                .toArray(new String[0]))
        .build();
  }

  @Override
  public UserDetails updatePassword(UserDetails user, String newPassword) {
    UserEntity userEntity = this.userService.updateUserPassword(user.getUsername(), newPassword);
    return withNewPassword(userEntity, newPassword);
  }

  private UserDetails withNewPassword(UserEntity userEntity, String newPassword) {
    return User.builder().username(userEntity.getUsername()).password(newPassword).build();
  }
}
