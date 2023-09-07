package vn.cmax.cafe.auth;

import java.security.NoSuchAlgorithmException;
import javax.servlet.http.HttpServletResponse;
import vn.cmax.cafe.api.models.AuthenticationRequest;
import vn.cmax.cafe.api.models.AuthenticationResponse;
import vn.cmax.cafe.exception.CmaxException;
import vn.cmax.cafe.exception.UnauthorizedException;import vn.cmax.cafe.user.UserEntity;

public interface AuthenticationService {
  AuthenticationResponse login(
      AuthenticationRequest authenticationRequest, HttpServletResponse response)
      throws CmaxException;

    UserEntity getCurrentAuthenticatedUser()throws CmaxException;

    boolean isSuperUser(UserEntity userEntity);
}
