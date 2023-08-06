package vn.cmax.cafe.api;

import vn.cmax.cafe.api.models.ApiError;
import vn.cmax.cafe.api.models.Role;
import vn.cmax.cafe.api.models.UserRequest;
import vn.cmax.cafe.api.models.UserSearchResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import vn.cmax.cafe.user.UserService;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(
    value = "io.swagger.codegen.v3.generators.java.SpringCodegen",
    date = "2023-08-05T19:16:24.213703+07:00[Asia/Ho_Chi_Minh]")
@RestController
public class UsersApiController implements UsersApi {

  private static final Logger log = LoggerFactory.getLogger(UsersApiController.class);

  private final ObjectMapper objectMapper;

  private final HttpServletRequest request;

  private final UserService userService;

  @org.springframework.beans.factory.annotation.Autowired
  public UsersApiController(ObjectMapper objectMapper, HttpServletRequest request, UserService userService) {
    this.objectMapper = objectMapper;
    this.request = request;
    this.userService = userService;
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

  public ResponseEntity<Void> usersIdPut(
      @Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema())
          @PathVariable("id")
          Integer id,
      @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema())
          @Valid
          @RequestBody
          UserRequest body) {
    String accept = request.getHeader("Accept");
    return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
  }

  public ResponseEntity<Void> usersNewPost(
      @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema())
          @Valid
          @RequestBody
          UserRequest body) {
    String accept = request.getHeader("Accept");
  this.userService.signUp(body);
    return new ResponseEntity<Void>(
            HttpStatus.OK
    );
  }
}
