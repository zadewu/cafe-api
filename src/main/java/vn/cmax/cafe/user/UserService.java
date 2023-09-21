package vn.cmax.cafe.user;

import vn.cmax.cafe.api.models.Role;
import vn.cmax.cafe.api.models.UserRequest;
import vn.cmax.cafe.api.models.UserSearchResponse;import vn.cmax.cafe.exception.CmaxException;

public interface UserService {
  UserEntity updateUserPassword(String username, String newPassword) throws CmaxException;

  UserEntity getUser(String username) throws CmaxException;

  UserEntity getUser(Long id) throws CmaxException;

  UserEntity signUp(UserRequest userRequest) throws CmaxException;

  UserEntity updateUser(Long id, UserRequest user) throws CmaxException;

  void deleteUser(Long id) throws CmaxException;

  UserSearchResponse findAllUser(int page, int pageSize, Role role) throws CmaxException;
}
