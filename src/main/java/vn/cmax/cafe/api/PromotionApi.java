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
import javax.validation.Valid;
import javax.validation.constraints.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import vn.cmax.cafe.api.models.ApiError;
import vn.cmax.cafe.api.models.Promotion;
import vn.cmax.cafe.api.models.PromotionPostRequest;
import vn.cmax.cafe.api.models.PromotionPutRequest;
import vn.cmax.cafe.api.models.PromotionSearchResponse;

@javax.annotation.Generated(
    value = "io.swagger.codegen.v3.generators.java.SpringCodegen",
    date = "2023-08-27T21:24:51.194362+07:00[Asia/Ho_Chi_Minh]")
@Validated
public interface PromotionApi {

  @Operation(
      summary = "Search all promotions",
      description = "Search all promotions",
      tags = {"promotion"})
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "OK",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PromotionSearchResponse.class))),
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
      value = "/promotion",
      produces = {"application/json"},
      method = RequestMethod.GET)
  ResponseEntity<PromotionSearchResponse> promotionGet();

  @Operation(
      summary = "Search promotion by Id",
      description = "Search promotion by Id",
      tags = {"promotion"})
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "OK",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Promotion.class))),
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
      value = "/promotion/{id}",
      produces = {"application/json"},
      method = RequestMethod.GET)
  ResponseEntity<Promotion> promotionIdGet(
      @Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema())
          @PathVariable("id")
          Long id);

  @Operation(
      summary = "Update promotion",
      description = "Update promotion",
      security = {@SecurityRequirement(name = "BearerAuth")},
      tags = {"promotion"})
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "204", description = "No Content"),
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
      value = "/promotion/{id}",
      produces = {"application/json"},
      consumes = {"application/json"},
      method = RequestMethod.PUT)
  ResponseEntity<Void> promotionIdPut(
      @Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema())
          @PathVariable("id")
          Long id,
      @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema())
          @Valid
          @RequestBody
          PromotionPutRequest body);

  @Operation(
      summary = "Create new promotion",
      description = "Create new promotion",
      tags = {"promotion"})
  @ApiResponses(
      value = {
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
      value = "/promotion",
      produces = {"application/json"},
      consumes = {"application/json"},
      method = RequestMethod.POST)
  ResponseEntity<Void> promotionPost(
      @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema())
          @Valid
          @RequestBody
          PromotionPostRequest body);
}