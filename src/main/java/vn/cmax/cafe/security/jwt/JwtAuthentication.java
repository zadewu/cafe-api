package vn.cmax.cafe.security.jwt;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import vn.cmax.cafe.exception.ApiErrorType;

@Getter
@Setter
@Builder
public class JwtAuthentication {
    private boolean success;

    private String message;

    private Authentication authentication;

    private ApiErrorType errorType;
}
