package vn.cmax.cafe.api;

import vn.cmax.cafe.api.models.ApiError;
import vn.cmax.cafe.api.models.Category;
import vn.cmax.cafe.api.models.CategoryRequest;
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
    date = "2023-08-15T22:58:35.895914+07:00[Asia/Ho_Chi_Minh]")
@RestController
public class CategoryApiController implements CategoryApi {

  private static final Logger log = LoggerFactory.getLogger(CategoryApiController.class);

  private final ObjectMapper objectMapper;

  private final HttpServletRequest request;
  private final MovieCategoryService categoryService;

  @org.springframework.beans.factory.annotation.Autowired
  public CategoryApiController(ObjectMapper objectMapper, HttpServletRequest request, MovieCategoryService categoryService) {
    this.objectMapper = objectMapper;
    this.request = request;
    this.categoryService = categoryService;
  }

  public ResponseEntity<CategorySearchResponse> categoryGet(
      @Min(0)
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
      CategorySearchResponse response = this.categoryService.findAll(page, pageSize);
      return new ResponseEntity<>(response, HttpStatus.OK);
    }

    return new ResponseEntity<CategorySearchResponse>(HttpStatus.NOT_IMPLEMENTED);
  }

  public ResponseEntity<Category> categoryIdGet(
      @Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema())
          @PathVariable("id")
          Long id) {
    String accept = request.getHeader("Accept");
    if (accept != null && accept.contains("application/json")) {
      Category category = this.categoryService.findById(id);
      if (category == null) {
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(category, HttpStatus.OK);
    }

    return new ResponseEntity<Category>(HttpStatus.NOT_IMPLEMENTED);
  }

  public ResponseEntity<Void> categoryIdPut(
      @Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema())
          @PathVariable("id")
          Long id,
      @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema())
          @Valid
          @RequestBody
          CategoryRequest body) {
    String accept = request.getHeader("Accept");
    return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
  }
}
