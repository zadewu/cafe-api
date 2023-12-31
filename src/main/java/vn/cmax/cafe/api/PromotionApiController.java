package vn.cmax.cafe.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.cmax.cafe.api.models.Promotion;
import vn.cmax.cafe.api.models.PromotionPostRequest;
import vn.cmax.cafe.api.models.PromotionPutRequest;
import vn.cmax.cafe.api.models.PromotionSearchResponse;
import vn.cmax.cafe.exception.ApiErrors;
import vn.cmax.cafe.exception.CmaxException;
import vn.cmax.cafe.promotion.PromotionService;

@javax.annotation.Generated(
    value = "io.swagger.codegen.v3.generators.java.SpringCodegen",
    date = "2023-09-06T22:55:26.603189+07:00[Asia/Ho_Chi_Minh]")
@RestController
@Slf4j
public class PromotionApiController implements PromotionApi {
  private final ObjectMapper objectMapper;

  private final HttpServletRequest request;
  private final PromotionService promotionService;

  @org.springframework.beans.factory.annotation.Autowired
  public PromotionApiController(
      ObjectMapper objectMapper, HttpServletRequest request, PromotionService promotionService) {
    this.objectMapper = objectMapper;
    this.request = request;
    this.promotionService = promotionService;
  }

  public ResponseEntity<PromotionSearchResponse> promotionGet(
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
          Integer pageSize,
      @Parameter(in = ParameterIn.QUERY, description = "", schema = @Schema())
          @Valid
          @RequestParam(value = "keyWord", required = false)
          String keyword) {
    PromotionSearchResponse response = this.promotionService.findAllPromotion(page, pageSize, keyword);
    return new ResponseEntity<PromotionSearchResponse>(response, HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ADMIN') or hasRole('AUDITOR')")
  @Override
  public ResponseEntity<Void> promotionIdDelete(Long id) {
    try {
      this.promotionService.deletePromotion(id);
      return new ResponseEntity(HttpStatus.NO_CONTENT);
    } catch (CmaxException e) {
      return ApiErrors.of(e);
    }
  }

  public ResponseEntity<Promotion> promotionIdGet(
      @Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema())
          @PathVariable("id")
          Long id) {

    Promotion promotion = null;
    try {
      promotion = this.promotionService.findPromotionWith(id);
    } catch (CmaxException e) {
      return ApiErrors.of(e);
    }
    return new ResponseEntity<Promotion>(promotion, HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ADMIN') or hasRole('AUDITOR')")
  public ResponseEntity<Void> promotionIdPut(
      @Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema())
          @PathVariable("id")
          Long id,
      @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema())
          @Valid
          @RequestBody
          PromotionPutRequest body) {
    try {
      this.promotionService.updatePromotion(id, body);
      return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
    } catch (CmaxException e) {
      return ApiErrors.of(e);
    }
  }

  @PreAuthorize("hasRole('ADMIN') or hasRole('AUDITOR')")
  public ResponseEntity<Void> promotionPost(
      @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema())
          @Valid
          @RequestBody
          PromotionPostRequest body) {
    try {
      this.promotionService.createNewPromotion(body);
      return new ResponseEntity<Void>(HttpStatus.CREATED);
    } catch (CmaxException e) {
      return ApiErrors.of(e);
    }
  }
}
