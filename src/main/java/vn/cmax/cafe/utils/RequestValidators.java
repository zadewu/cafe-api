package vn.cmax.cafe.utils;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import vn.cmax.cafe.api.models.ApiError;
import vn.cmax.cafe.api.models.Role;
import vn.cmax.cafe.api.models.UserRequest;

import java.util.Objects;

@UtilityClass
public class RequestValidators {
    public static void validateUserRequest(UserRequest userRequest) {
        boolean match = userRequest.getRoles().stream().filter(Objects::nonNull).anyMatch(item -> item != Role.ADMIN);
        if (match) {
            throw new IllegalArgumentException("Roles are not valid");
        }
    }
}
