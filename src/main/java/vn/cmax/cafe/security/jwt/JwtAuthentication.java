package vn.cmax.cafe.security.jwt;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;

@Getter
@Setter
@Builder
public class JwtAuthentication {
    private boolean success;

    private String message;

    private Authentication authentication;
}
