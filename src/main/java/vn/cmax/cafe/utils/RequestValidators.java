package vn.cmax.cafe.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import vn.cmax.cafe.api.models.UserRequest;
import vn.cmax.cafe.exception.CmaxException;
import vn.cmax.cafe.exception.ValidationException;

@UtilityClass
public class RequestValidators {
  public static void validateUserRequest(UserRequest userRequest) throws CmaxException {
        if (StringUtils.isBlank(userRequest.getUsername())) {
            throw new ValidationException("Username is blank");
        }
        if (StringUtils.isBlank(userRequest.getEmail())) {
            throw new ValidationException("Email is blank");
        }
        if (StringUtils.isBlank(userRequest.getFirstName())) {
            throw new ValidationException("Firstname is blank");
        }
        if (StringUtils.isBlank(userRequest.getLastName())) {
            throw new ValidationException("Lastname is blank");
        }
    }

}
