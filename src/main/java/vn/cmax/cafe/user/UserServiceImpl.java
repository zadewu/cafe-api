package vn.cmax.cafe.user;

import java.util.Optional;
import java.util.Set;
import javax.transaction.Transactional;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.cmax.cafe.api.models.User;
import vn.cmax.cafe.api.models.UserRequest;
import vn.cmax.cafe.auth.AuthenticationService;
import vn.cmax.cafe.exception.CmaxException;
import vn.cmax.cafe.exception.ValidationException;
import vn.cmax.cafe.mapper.RoleMapper;
import vn.cmax.cafe.mapper.UserMapper;
import vn.cmax.cafe.utils.RequestValidators;
import vn.cmax.cafe.utils.UserRequests;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {
  private UserRepository userRepository;
  private PasswordEncoder passwordEncoder;

  private AuthenticationService authenticationService;

  @Transactional
  @Override
  public UserEntity updateUserPassword(String username, String newPassword) {
    UserEntity userEntity = getUser(username);
    userEntity.setPassword(newPassword);
    this.userRepository.save(userEntity);
    return getUser(username);
  }

  @Override
  public UserEntity getUser(String username) {
    Optional<UserEntity> userEntityOptional = this.userRepository.findByUsername(username);
    return userEntityOptional.stream().findFirst().orElseThrow();
  }

  @Override
  public UserEntity getUser(Long id) {
    Optional<UserEntity> userEntityOptional = this.userRepository.findById(id);
    return userEntityOptional.stream().findFirst().orElseThrow();
  }

  @Transactional
  @Override
  public UserEntity signUp(UserRequest userRequest) {
    User user = UserRequests.toUser(userRequest);
    UserEntity userEntity = UserMapper.INSTANCE.fromUser(user);
    userEntity.setPassword(passwordEncoder.encode(userRequest.getPassword()));
    return this.userRepository.save(userEntity);
  }

  @Transactional
  @Override
  public UserEntity updateUser(Long id, UserRequest userRequest) {
    RequestValidators.validateUserRequest(userRequest);
    UserEntity updatedUser = getUser(id);
    if (StringUtils.isNotBlank(userRequest.getPassword())) {
      if (!passwordEncoder.matches(userRequest.getPassword(), updatedUser.getPassword())) {
        updatedUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
      }
    }
    updatedUser.setUsername(userRequest.getUsername());
    updatedUser.setFirstName(userRequest.getFirstName());
    updatedUser.setLastName(userRequest.getLastName());
    updatedUser.setEmail(userRequest.getEmail());
    if (userRequest.getRoles() != null && userRequest.getRoles().size() > 0) {
      UserEntity authUser = this.authenticationService.getCurrentAuthenticatedUser();
      if (this.authenticationService.isSuperUser(authUser)) {
        Set<UserRole> roles = RoleMapper.INSTANCE.toEntityRoles(userRequest.getRoles());
        updatedUser.setRoles(roles);
      }
    }
    return this.userRepository.save(updatedUser);
  }
}
