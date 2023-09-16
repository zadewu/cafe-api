package vn.cmax.cafe.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import vn.cmax.cafe.api.models.AuthenticationRequest;
import vn.cmax.cafe.api.models.AuthenticationResponse;
import vn.cmax.cafe.api.models.RefreshTokenResponse;
import vn.cmax.cafe.exception.CmaxException;
import vn.cmax.cafe.user.UserEntity;

public interface AuthenticationService {
  AuthenticationResponse login(
      AuthenticationRequest authenticationRequest, HttpServletResponse response)
      throws CmaxException;

  UserEntity getCurrentAuthenticatedUser() throws CmaxException;

  boolean isSuperUser(UserEntity userEntity);

  void logout(HttpServletRequest request, HttpServletResponse response) throws CmaxException;

  RefreshTokenResponse refreshToken(HttpServletRequest request, HttpServletResponse response)
      throws CmaxException;
}
