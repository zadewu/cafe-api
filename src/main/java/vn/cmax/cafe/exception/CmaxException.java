package vn.cmax.cafe.exception;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class CmaxException extends Exception {
    private HttpStatus status;

    public CmaxException(final String errorMessage) {
        super(errorMessage);
    }

    public CmaxException(final String errorMessage, final Throwable throwable) {
        super(errorMessage, throwable);
    }

    public CmaxException(final String errorMessage, final HttpStatus status) {
        this(errorMessage);
        this.status = status;
    }

    public CmaxException(final String errorMessage, final HttpStatus status, final Throwable throwable) {
        this(errorMessage, throwable);
        this.status = status;
    }
}
