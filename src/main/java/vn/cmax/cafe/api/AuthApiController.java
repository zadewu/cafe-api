package vn.cmax.cafe.api;

import vn.cmax.cafe.auth.AuthenticationService;
import vn.cmax.cafe.api.models.AuthenticationRequest;
import vn.cmax.cafe.api.models.AuthenticationResponse;
import vn.cmax.cafe.api.models.RefreshTokenResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@javax.annotation.Generated(
    value = "io.swagger.codegen.v3.generators.java.SpringCodegen",
    date = "2023-08-05T19:16:24.213703+07:00[Asia/Ho_Chi_Minh]")
@RestController
public class AuthApiController implements AuthApi {

  private static final Logger log = LoggerFactory.getLogger(AuthApiController.class);

  private final ObjectMapper objectMapper;

  private final HttpServletRequest request;
  private final AuthenticationService authenticationService;

  @org.springframework.beans.factory.annotation.Autowired
  public AuthApiController(
      ObjectMapper objectMapper,
      HttpServletRequest request,
      AuthenticationService authenticationService) {
    this.objectMapper = objectMapper;
    this.request = request;
    this.authenticationService = authenticationService;
  }

  public ResponseEntity<AuthenticationResponse> authLoginPost(
      @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema())
          @Valid
          @RequestBody
          AuthenticationRequest body,
      HttpServletResponse response) {
    String accept = request.getHeader("Accept");
    if (accept != null && accept.contains("application/json")) {
      try {
        return new ResponseEntity<AuthenticationResponse>(
            this.authenticationService.login(body, response), HttpStatus.OK);
      } catch (NoSuchAlgorithmException e) {
        log.error("Couldn't process login", e);
        return new ResponseEntity<AuthenticationResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    return new ResponseEntity<AuthenticationResponse>(HttpStatus.NOT_IMPLEMENTED);
  }

  public ResponseEntity<Void> authLogoutPost() {
    String accept = request.getHeader("Accept");
    return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
  }

  public ResponseEntity<RefreshTokenResponse> authRefreshPost() {
    String accept = request.getHeader("Accept");
    if (accept != null && accept.contains("application/json")) {
      try {
        return new ResponseEntity<RefreshTokenResponse>(
            objectMapper.readValue(
                "{\n  \"accessToken\" : \"accessToken\"\n}", RefreshTokenResponse.class),
            HttpStatus.NOT_IMPLEMENTED);
      } catch (IOException e) {
        log.error("Couldn't serialize response for content type application/json", e);
        return new ResponseEntity<RefreshTokenResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    return new ResponseEntity<RefreshTokenResponse>(HttpStatus.NOT_IMPLEMENTED);
  }
}
