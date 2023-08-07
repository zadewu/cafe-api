package vn.cmax.cafe.user;

import vn.cmax.cafe.api.models.UserRequest;
import vn.cmax.cafe.exception.CmaxException;

import javax.xml.bind.ValidationException;

public interface UserService {
    UserEntity updateUserPassword(String username, String newPassword);

    UserEntity getUser(String username);

    UserEntity getUser(Long id);

    UserEntity signUp(UserRequest userRequest);

  UserEntity updateUser(Long id, UserRequest user);
}
