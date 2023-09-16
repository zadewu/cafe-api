package vn.cmax.cafe.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends CmaxException {
    public ForbiddenException() {
        super(ApiErrorMessages.UNAUTHORIZED, HttpStatus.FORBIDDEN);
    }

    public ForbiddenException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }

    public ForbiddenException(String message, Throwable throwable) {
        super(message, HttpStatus.FORBIDDEN, throwable);
    }
}
