package vn.cmax.cafe.user;

import vn.cmax.cafe.api.models.UserRequest;
import vn.cmax.cafe.exception.CmaxException;import vn.cmax.cafe.exception.ValidationException;

public interface UserService {
    UserEntity updateUserPassword(String username, String newPassword)throws CmaxException;

    UserEntity getUser(String username)throws CmaxException;

    UserEntity getUser(Long id);

  UserEntity signUp(UserRequest userRequest) throws CmaxException;

  UserEntity updateUser(Long id, UserRequest user) throws CmaxException;
}
