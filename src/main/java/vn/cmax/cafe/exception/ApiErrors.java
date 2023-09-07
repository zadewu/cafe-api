package vn.cmax.cafe.exception;

import lombok.experimental.UtilityClass;
import org.springframework.http.ResponseEntity;
import vn.cmax.cafe.api.models.ApiError;

@UtilityClass
public class ApiErrors {
  public static ResponseEntity of(CmaxException exception) {
    ApiError error = new ApiError();
    error.code(exception.getStatus().value()).message(exception.getMessage());
    return new ResponseEntity(error, exception.getStatus());
  }
}
