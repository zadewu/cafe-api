package vn.cmax.cafe.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends CmaxException {

  public UnauthorizedException() {
    this(ApiErrorMessages.UNAUTHORIZED);
  }

  public UnauthorizedException(String message) {
    super(message, HttpStatus.UNAUTHORIZED);
  }
}
