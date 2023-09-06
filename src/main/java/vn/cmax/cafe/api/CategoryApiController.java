package vn.cmax.cafe.api;

import lombok.extern.slf4j.Slf4j;
import vn.cmax.cafe.api.models.ApiError;
import vn.cmax.cafe.api.models.Category;
import vn.cmax.cafe.api.models.CategoryPostRequest;
import vn.cmax.cafe.api.models.CategoryPutRequest;
import vn.cmax.cafe.api.models.CategorySearchResponse;
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
import vn.cmax.cafe.category.MovieCategoryService;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(
    value = "io.swagger.codegen.v3.generators.java.SpringCodegen",
    date = "2023-09-06T22:55:26.603189+07:00[Asia/Ho_Chi_Minh]")
@RestController
@Slf4j
public class CategoryApiController implements CategoryApi {

  private final ObjectMapper objectMapper;

  private final HttpServletRequest request;
  private final MovieCategoryService movieCategoryService;

  @org.springframework.beans.factory.annotation.Autowired
  public CategoryApiController(
      ObjectMapper objectMapper,
      HttpServletRequest request,
      MovieCategoryService movieCategoryService) {
    this.objectMapper = objectMapper;
    this.request = request;
    this.movieCategoryService = movieCategoryService;
  }

  public ResponseEntity<CategorySearchResponse> categoryGet(
      @Min(0)
          @Parameter(
              in = ParameterIn.QUERY,
              description = "",
              schema = @Schema(allowableValues = {}))
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
      CategorySearchResponse response = this.movieCategoryService.findAll(page, pageSize);
      return new ResponseEntity<CategorySearchResponse>(response, HttpStatus.OK);
    }
    return new ResponseEntity<CategorySearchResponse>(HttpStatus.BAD_REQUEST);
  }

  public ResponseEntity<Category> categoryIdGet(
      @Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema())
          @PathVariable("id")
          Long id) {
    String accept = request.getHeader("Accept");
    if (accept != null && accept.contains("application/json")) {
      Category category = this.movieCategoryService.findById(id);
      return new ResponseEntity<>(category, HttpStatus.OK);
    }
    return new ResponseEntity<Category>(HttpStatus.BAD_REQUEST);
  }

  public ResponseEntity<Void> categoryIdPut(
      @Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema())
          @PathVariable("id")
          Long id,
      @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema())
          @Valid
          @RequestBody
          CategoryPutRequest body) {
    String accept = request.getHeader("Accept");
      if (accept != null && accept.contains("application/json")) {
          this.movieCategoryService.updateCategory(id, body);
          return new ResponseEntity<>(HttpStatus.ACCEPTED);
      }
    return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
  }

  public ResponseEntity<Void> categoryPost(
      @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema())
          @Valid
          @RequestBody
          CategoryPostRequest body) {
    String accept = request.getHeader("Accept");
      if (accept != null && accept.contains("application/json")) {
          this.movieCategoryService.createNewCategory(body);
          return new ResponseEntity<>(HttpStatus.CREATED);
      }
    return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
  }
}
