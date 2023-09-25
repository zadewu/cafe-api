package vn.cmax.cafe.user;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.cmax.cafe.api.models.*;
import vn.cmax.cafe.auth.AuthenticationService;
import vn.cmax.cafe.exception.CmaxException;
import vn.cmax.cafe.exception.ValidationException;
import vn.cmax.cafe.mapper.RoleMapper;
import vn.cmax.cafe.mapper.UserMapper;
import vn.cmax.cafe.utils.Keywords;
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
  public UserEntity updateUserPassword(String username, String newPassword) throws CmaxException {
    UserEntity userEntity = getUser(username);
    userEntity.setPassword(newPassword);
    this.userRepository.save(userEntity);
    return getUser(username);
  }

  @Override
  public UserEntity getUser(String username) throws CmaxException {
    Optional<UserEntity> userEntityOptional = this.userRepository.findByUsername(username);
    return userEntityOptional.stream()
        .findFirst()
        .orElseThrow(() -> new ValidationException("Cannot find user with username " + username));
  }

  @Override
  public UserEntity getUser(Long id) throws CmaxException {
    Optional<UserEntity> userEntityOptional = this.userRepository.findById(id);
    return userEntityOptional.stream()
        .findFirst()
        .orElseThrow(() -> new ValidationException("Cannot find user with id " + id));
  }

  @Transactional
  @Override
  public UserEntity signUp(UserRequest userRequest) throws CmaxException {
    Optional<UserEntity> userEntityOptional =
        this.userRepository.findByUsernameOrEmail(
            userRequest.getUsername(), userRequest.getEmail());
    if (userEntityOptional.isPresent()) {
      throw new ValidationException(
          "User with username " + userRequest.getUsername() + " already exist");
    }

    User user = UserRequests.toUser(userRequest);
    UserEntity userEntity = UserMapper.INSTANCE.fromUser(user);
    userEntity.setPassword(passwordEncoder.encode(userRequest.getPassword()));
    return this.userRepository.save(userEntity);
  }

  @Transactional
  @Override
  public UserEntity updateUser(Long id, UserRequest userRequest) throws CmaxException {
    RequestValidators.validateUserRequest(userRequest);
    UserEntity updatedUser = getUser(id);
    if (StringUtils.isNotBlank(userRequest.getPassword())) {
      if (!passwordEncoder.matches(userRequest.getPassword(), updatedUser.getPassword())) {
        updatedUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
      }
    }
    Optional.ofNullable(userRequest.getUsername()).ifPresent(item -> updatedUser.setUsername(item));
    Optional.ofNullable(userRequest.getFirstName())
        .ifPresent(item -> updatedUser.setFirstName(item));
    Optional.ofNullable(userRequest.getLastName()).ifPresent(item -> updatedUser.setLastName(item));
    Optional.ofNullable(userRequest.getEmail()).ifPresent(item -> updatedUser.setEmail(item));
    Optional.ofNullable(userRequest.getPhoneNumber())
        .ifPresent(item -> updatedUser.setPhoneNumber(item));
    if (userRequest.getRoles() != null && userRequest.getRoles().size() > 0) {
      UserEntity authUser = this.authenticationService.getCurrentAuthenticatedUser();
      if (this.authenticationService.isSuperUser(authUser)) {
        Set<UserRole> roles = RoleMapper.INSTANCE.toEntityRoles(userRequest.getRoles());
        updatedUser.setRoles(roles);
      }
    }
    return this.userRepository.save(updatedUser);
  }

  @Override
  @Transactional
  public void deleteUser(Long id) throws CmaxException {
    UserEntity userEntity = this.getUser(id);
    this.userRepository.delete(userEntity);
  }

  @Override
  public UserSearchResponse findAllUser(int page, int pageSize, Role role, String keyword)
      throws CmaxException {
    Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "updatedDate"));
    UserSearchResponse response = new UserSearchResponse().records(new ArrayList<>());
    UserRole convertRole = null;
    if (role != null) {
      convertRole = UserRole.valueOf(role.name());
    }
    Specification<UserEntity> userEntitySpecification =
        Keywords.fromUserKeyword(convertRole, keyword);
    Page<UserEntity> userEntityPage =
        this.userRepository.findAll(userEntitySpecification, pageable);
    Page<User> users = userEntityPage.map(item -> UserMapper.INSTANCE.fromEntity(item));
    response
        .records(users.getContent())
        .pageSize(users.getSize())
        .totalPage(users.getTotalPages())
        .totalCount(users.getTotalElements())
        .currentPage(users.getNumber());
    return response;
  }
}
