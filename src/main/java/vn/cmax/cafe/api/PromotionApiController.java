package vn.cmax.cafe.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vn.cmax.cafe.api.models.Promotion;
import vn.cmax.cafe.api.models.PromotionPostRequest;
import vn.cmax.cafe.api.models.PromotionPutRequest;
import vn.cmax.cafe.api.models.PromotionSearchResponse;

@javax.annotation.Generated(
    value = "io.swagger.codegen.v3.generators.java.SpringCodegen",
    date = "2023-08-27T21:24:51.194362+07:00[Asia/Ho_Chi_Minh]")
@RestController
public class PromotionApiController implements PromotionApi {

  private static final Logger log = LoggerFactory.getLogger(PromotionApiController.class);

  private final ObjectMapper objectMapper;

  private final HttpServletRequest request;

  @org.springframework.beans.factory.annotation.Autowired
  public PromotionApiController(ObjectMapper objectMapper, HttpServletRequest request) {
    this.objectMapper = objectMapper;
    this.request = request;
  }

  public ResponseEntity<PromotionSearchResponse> promotionGet() {
    String accept = request.getHeader("Accept");
    if (accept != null && accept.contains("application/json")) {
      try {
        return new ResponseEntity<PromotionSearchResponse>(
            objectMapper.readValue("\"\"", PromotionSearchResponse.class),
            HttpStatus.NOT_IMPLEMENTED);
      } catch (IOException e) {
        log.error("Couldn't serialize response for content type application/json", e);
        return new ResponseEntity<PromotionSearchResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    return new ResponseEntity<PromotionSearchResponse>(HttpStatus.NOT_IMPLEMENTED);
  }

  public ResponseEntity<Promotion> promotionIdGet(
      @Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema())
          @PathVariable("id")
          Long id) {
    String accept = request.getHeader("Accept");
    if (accept != null && accept.contains("application/json")) {
      try {
        return new ResponseEntity<Promotion>(
            objectMapper.readValue(
                "{\n  \"id\" : 0,\n  \"title\" : \"title\",\n  \"content\" : \"content\"\n}",
                Promotion.class),
            HttpStatus.NOT_IMPLEMENTED);
      } catch (IOException e) {
        log.error("Couldn't serialize response for content type application/json", e);
        return new ResponseEntity<Promotion>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    return new ResponseEntity<Promotion>(HttpStatus.NOT_IMPLEMENTED);
  }

  public ResponseEntity<Void> promotionIdPut(
      @Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema())
          @PathVariable("id")
          Long id,
      @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema())
          @Valid
          @RequestBody
          PromotionPutRequest body) {
    String accept = request.getHeader("Accept");
    return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
  }

  public ResponseEntity<Void> promotionPost(
      @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema())
          @Valid
          @RequestBody
          PromotionPostRequest body) {
    String accept = request.getHeader("Accept");
    return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
  }
}
