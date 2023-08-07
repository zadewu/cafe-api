package vn.cmax.cafe.utils;

import lombok.experimental.UtilityClass;
import vn.cmax.cafe.api.models.Role;
import vn.cmax.cafe.api.models.User;
import vn.cmax.cafe.api.models.UserRequest;

import java.util.ArrayList;

@UtilityClass
public class UserRequests {
    public static User toUser(UserRequest userRequest) {
        User user = new User();
        user.username(userRequest.getUsername())
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .email(userRequest.getEmail())
                .phoneNumber(userRequest.getPhoneNumber());
        if (userRequest.getRoles() == null) {
            user.roles(new ArrayList<>());
            user.addRolesItem(Role.USER);
        } else {
            user.roles(userRequest.getRoles());
        }
        return user;
    }
}
