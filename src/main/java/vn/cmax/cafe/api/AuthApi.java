/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.27).
 * https://github.com/swagger-api/swagger-codegen Do not edit the class manually.
 */
package vn.cmax.cafe.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import vn.cmax.cafe.api.models.ApiError;
import vn.cmax.cafe.api.models.AuthenticationRequest;
import vn.cmax.cafe.api.models.AuthenticationResponse;
import vn.cmax.cafe.api.models.RefreshTokenResponse;

@javax.annotation.Generated(
    value = "io.swagger.codegen.v3.generators.java.SpringCodegen",
    date = "2023-08-05T19:16:24.213703+07:00[Asia/Ho_Chi_Minh]")
@Validated
public interface AuthApi {

  @Operation(
      summary = "Process login with username and password",
      description = "",
      tags = {"auth"})
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "OK",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AuthenticationResponse.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Not found",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiError.class)))
      })
  @RequestMapping(
      value = "/auth/login",
      produces = {"application/json"},
      consumes = {"application/json"},
      method = RequestMethod.POST)
  ResponseEntity<AuthenticationResponse> authLoginPost(
      @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema())
          @Valid
          @RequestBody
          AuthenticationRequest body);

  @Operation(
      summary = "Logout with accessToken",
      description = "Logout with accessToken",
      security = {@SecurityRequirement(name = "BearerAuth")},
      tags = {"auth"})
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Logout successfully"),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiError.class))),
        @ApiResponse(
            responseCode = "500",
            description = "Unauthorized",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiError.class)))
      })
  @RequestMapping(
      value = "/auth/logout",
      produces = {"application/json"},
      method = RequestMethod.POST)
  ResponseEntity<Void> authLogoutPost();

  @Operation(
      summary = "Grant new access token",
      description = "Grant another accessToken",
      tags = {"auth"})
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Grant another accessToken",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RefreshTokenResponse.class)))
      })
  @RequestMapping(
      value = "/auth/refresh",
      produces = {"application/json"},
      method = RequestMethod.POST)
  ResponseEntity<RefreshTokenResponse> authRefreshPost();
}
