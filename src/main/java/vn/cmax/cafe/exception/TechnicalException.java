package vn.cmax.cafe.exception;

import org.springframework.http.HttpStatus;

public class TechnicalException extends CmaxException {

    public TechnicalException(String errorMessage) {
        super(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public TechnicalException(String errorMessage, Throwable throwable) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage, throwable);
    }
}
