package vn.cmax.cafe.exception;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class CmaxSecurityException extends SecurityException {
    public static final String INVALID_USERNAME_PASSWORD_ERR_MESSAGE =
            "Invalid username/password supplied";
    public static final String MISSING_AUTHENTICATED_USER =
            "Current authenticated user is not present";
    public static final String PRINCIPAL_IS_NOT_A_USER_ENTITY = "Principal is not a user entity";
    public static final String BROKER_TRIED_TO_ACCESS_OR_UPDATE_OTHER_BROKERS_DOSSIER =
            "Broker %s tried to access/update/delete other broker's dossier";
    public static final String USER_IS_DEACTIVATED = "User is deactivated";
    public static final String USER_IS_DELETED = "User is deleted";
    private HttpStatus httpStatus;

    public CmaxSecurityException(final String errorMessage) {
        super(errorMessage);
    }

    public CmaxSecurityException(final String errorMessage, final Throwable cause) {
        super(errorMessage, cause);
    }

    public CmaxSecurityException(final String errorMessage, final HttpStatus httpStatus) {
        this(errorMessage);
        this.httpStatus = httpStatus;
    }

    public CmaxSecurityException(
            final String errorMessage, final Throwable cause, final HttpStatus httpStatus) {
        this(errorMessage, cause);
        this.httpStatus = httpStatus;
    }
}
