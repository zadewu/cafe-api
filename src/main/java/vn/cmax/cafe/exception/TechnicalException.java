package vn.cmax.cafe.exception;

import org.springframework.http.HttpStatus;

public class TechnicalException extends CmaxException {

    public TechnicalException() {
        super(ApiErrorMessages.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public TechnicalException(Throwable throwable) {
        super(ApiErrorMessages.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR, throwable);
    }
}
