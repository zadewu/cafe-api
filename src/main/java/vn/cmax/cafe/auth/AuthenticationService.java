package vn.cmax.cafe.auth;

import java.security.NoSuchAlgorithmException;
import javax.servlet.http.HttpServletResponse;
import vn.cmax.cafe.api.models.AuthenticationRequest;
import vn.cmax.cafe.api.models.AuthenticationResponse;
import vn.cmax.cafe.user.UserEntity;

public interface AuthenticationService {
    AuthenticationResponse login(AuthenticationRequest authenticationRequest, HttpServletResponse response) throws NoSuchAlgorithmException;

    UserEntity getCurrentAuthenticatedUser();

    boolean isSuperUser(UserEntity userEntity);
}
