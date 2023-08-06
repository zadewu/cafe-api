package vn.cmax.cafe.user;

import vn.cmax.cafe.api.models.UserRequest;

public interface UserService {
    UserEntity updateUserPassword(String username, String newPassword);

    UserEntity getUser(String username);

    UserEntity getUser(Long id);

    UserEntity signUp(UserRequest userRequest);
}
