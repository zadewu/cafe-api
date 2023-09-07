package vn.cmax.cafe.exception;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ApiErrorMessages {
  public static final String INVALID_USERNAME_PASSWORD_ERR_MESSAGE =
      "Invalid username/password provided";
  public static final String UNAUTHORIZED = "User do not have permission to perform this request";
  public static final String INTERNAL_SERVER_ERROR =
      "Some issues happened unexpectedly. Please retry later";
  public static final String MISSING_AUTHENTICATED_USER = "Authenticate user is missing";
  public static final String PRINCIPAL_IS_NOT_A_USER_ENTITY = "Current principal is not user entity";
}
