package vn.cmax.cafe.exception;

import org.springframework.http.HttpStatus;

public class CmaxSecurityException extends CmaxException {
    public static final String INVALID_USERNAME_PASSWORD_ERR_MESSAGE =
            "Invalid username/password supplied";
    public static final String MISSING_AUTHENTICATED_USER =
            "Current authenticated user is not present";
    public static final String PRINCIPAL_IS_NOT_A_USER_ENTITY = "Principal is not a user entity";
    public CmaxSecurityException(final String errorMessage) {
        super(errorMessage);
    }

    public CmaxSecurityException(final String errorMessage, final Throwable cause) {
        super(errorMessage, cause);
    }

    public CmaxSecurityException(final String errorMessage, final HttpStatus httpStatus) {
        super(errorMessage, httpStatus);
    }

    public CmaxSecurityException(
            final String errorMessage, final Throwable cause, final HttpStatus httpStatus) {
        super(httpStatus, errorMessage, cause);
    }
}
