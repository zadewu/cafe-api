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
import java.util.List;
import javax.validation.constraints.*;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import vn.cmax.cafe.api.models.ApiError;
import vn.cmax.cafe.api.models.FileUploadResponse;

@javax.annotation.Generated(
    value = "io.swagger.codegen.v3.generators.java.SpringCodegen",
    date = "2023-09-07T19:51:51.992732+07:00[Asia/Ho_Chi_Minh]")
@Validated
public interface FileApi {

  @Operation(
      summary = "Upload file",
      description = "Upload file",
      security = {@SecurityRequirement(name = "BearerAuth")},
      tags = {"file"})
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "201", description = "Created"),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiError.class))),
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
      value = "/file/upload",
      produces = {"application/json"},
      consumes = {"multipart/*"},
      method = RequestMethod.POST)
  ResponseEntity<FileUploadResponse> fileUploadPost(
      @Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema())
          @RequestParam(value = "file", required = false)
          MultipartFile file);
}
