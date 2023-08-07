package vn.cmax.cafe.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.IOException;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.cmax.cafe.api.models.ApiError;
import vn.cmax.cafe.api.models.Role;
import vn.cmax.cafe.api.models.UserRequest;
import vn.cmax.cafe.api.models.UserSearchResponse;
import vn.cmax.cafe.auth.AuthenticationService;
import vn.cmax.cafe.exception.CmaxException;
import vn.cmax.cafe.user.UserEntity;
import vn.cmax.cafe.user.UserService;

@javax.annotation.Generated(
    value = "io.swagger.codegen.v3.generators.java.SpringCodegen",
    date = "2023-08-05T19:16:24.213703+07:00[Asia/Ho_Chi_Minh]")
@RestController
public class UsersApiController implements UsersApi {

  private static final Logger log = LoggerFactory.getLogger(UsersApiController.class);

  private final ObjectMapper objectMapper;

  private final HttpServletRequest request;

  private final UserService userService;

  private final AuthenticationService authenticationService;

  @org.springframework.beans.factory.annotation.Autowired
  public UsersApiController(
      ObjectMapper objectMapper,
      HttpServletRequest request,
      UserService userService,
      AuthenticationService authenticationService) {
    this.objectMapper = objectMapper;
    this.request = request;
    this.userService = userService;
    this.authenticationService = authenticationService;
  }

  public ResponseEntity<UserSearchResponse> usersGet(
      @Parameter(in = ParameterIn.QUERY, description = "", schema = @Schema())
          @Valid
          @RequestParam(value = "role", required = false)
          Role role,
      @Min(1)
          @Parameter(
              in = ParameterIn.QUERY,
              description = "",
              schema =
                  @Schema(
                      allowableValues = {},
                      minimum = "1"))
          @Valid
          @RequestParam(value = "page", required = false)
          Integer page,
      @Min(10)
          @Max(50)
          @Parameter(
              in = ParameterIn.QUERY,
              description = "",
              schema =
                  @Schema(
                      allowableValues = {},
                      minimum = "10",
                      maximum = "50"))
          @Valid
          @RequestParam(value = "pageSize", required = false)
          Integer pageSize) {
    String accept = request.getHeader("Accept");
    if (accept != null && accept.contains("application/json")) {
      try {
        return new ResponseEntity<UserSearchResponse>(
            objectMapper.readValue("\"\"", UserSearchResponse.class), HttpStatus.NOT_IMPLEMENTED);
      } catch (IOException e) {
        log.error("Couldn't serialize response for content type application/json", e);
        return new ResponseEntity<UserSearchResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    return new ResponseEntity<UserSearchResponse>(HttpStatus.NOT_IMPLEMENTED);
  }

  public ResponseEntity usersIdPut(
      @Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema())
          @PathVariable("id")
          Long id,
      @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema())
          @Valid
          @RequestBody
          UserRequest body) {
    String accept = request.getHeader("Accept");
    UserEntity userEntity = this.authenticationService.getCurrentAuthenticatedUser();
    if (userEntity == null) {
      ApiError error =
          new ApiError()
              .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
              .message("Cannot found current authenticated user");
      return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    if (!userEntity.getId().equals(id)) {
        if (!this.authenticationService.isSuperUser(userEntity)) {
            ApiError error =
                    new ApiError()
                            .code(HttpStatus.METHOD_NOT_ALLOWED.value())
                            .message("Current authenticate user do not have permission to update other user.");
            return new ResponseEntity(error, HttpStatus.METHOD_NOT_ALLOWED);
        }
    }
  if (body.getRoles() != null && body.getRoles().size() > 0) {
      if (!this.authenticationService.isSuperUser(userEntity)) {
          ApiError error =
                  new ApiError()
                          .code(HttpStatus.METHOD_NOT_ALLOWED.value())
                          .message("Current authenticate user do not have permission to update user's roles.");
          return new ResponseEntity(error, HttpStatus.METHOD_NOT_ALLOWED);
      }
  }
    try {
        this.userService.updateUser(id, body);
    } catch (CmaxException e) {
        ApiError error =
                new ApiError()
                        .code(e.getStatus().value())
                        .message(e.getMessage());
        return new ResponseEntity(error, e.getStatus());
    }
  return new ResponseEntity(HttpStatus.NO_CONTENT);
  }

  public ResponseEntity usersSignUpPost(
      @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema())
          @Valid
          @RequestBody
          UserRequest body) {
    String accept = request.getHeader("Accept");
    boolean match =
        body.getRoles().stream().filter(Objects::nonNull).anyMatch(item -> item == Role.ADMIN);
    if (match) {
      ApiError apiError = new ApiError();
      apiError.code(HttpStatus.BAD_REQUEST.value()).message("Roles are not valid");
      return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
    this.userService.signUp(body);
    return new ResponseEntity<Void>(HttpStatus.OK);
  }
}