package vn.cmax.cafe.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vn.cmax.cafe.api.models.AuthenticationRequest;
import vn.cmax.cafe.api.models.AuthenticationResponse;
import vn.cmax.cafe.api.models.RefreshTokenResponse;
import vn.cmax.cafe.auth.AuthenticationService;
import vn.cmax.cafe.exception.ApiErrors;
import vn.cmax.cafe.exception.CmaxException;

@javax.annotation.Generated(
    value = "io.swagger.codegen.v3.generators.java.SpringCodegen",
    date = "2023-08-05T19:16:24.213703+07:00[Asia/Ho_Chi_Minh]")
@RestController
public class AuthApiController implements AuthApi {

  private static final Logger log = LoggerFactory.getLogger(AuthApiController.class);

  private final ObjectMapper objectMapper;

  private final HttpServletRequest request;
  private final HttpServletResponse response;
  private final AuthenticationService authenticationService;

  @org.springframework.beans.factory.annotation.Autowired
  public AuthApiController(
      ObjectMapper objectMapper,
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationService authenticationService) {
    this.objectMapper = objectMapper;
    this.request = request;
    this.response = response;
    this.authenticationService = authenticationService;
  }

  public ResponseEntity authLoginPost(
      @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema())
          @Valid
          @RequestBody
          AuthenticationRequest body) {
    try {
      return new ResponseEntity<AuthenticationResponse>(
          this.authenticationService.login(body, response), HttpStatus.OK);
    } catch (CmaxException e) {
      return ApiErrors.of(e);
    }
  }

  public ResponseEntity<Void> authLogoutPost() {
    try {
      this.authenticationService.logout(request, response);
      return new ResponseEntity(HttpStatus.NO_CONTENT);
    } catch (CmaxException ex) {
      return ApiErrors.of(ex);
    }
  }

  public ResponseEntity<RefreshTokenResponse> authRefreshPost() {
    try {
      RefreshTokenResponse tokenResponse =
          this.authenticationService.refreshToken(request, response);
      return new ResponseEntity<>(tokenResponse, HttpStatus.OK);
    } catch (CmaxException e) {
      return ApiErrors.of(e);
    }
  }
}
