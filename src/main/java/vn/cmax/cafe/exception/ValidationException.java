package vn.cmax.cafe.exception;

import org.springframework.http.HttpStatus;

public class ValidationException extends CmaxException {

    public ValidationException(String errorMessage) {
    super(errorMessage, HttpStatus.BAD_REQUEST);
    }

    public ValidationException(String errorMessage, Throwable throwable) {
        super(errorMessage, HttpStatus.BAD_REQUEST, throwable);
    }
}
