package vn.cmax.cafe.api;

import java.util.Optional;
import javax.validation.constraints.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import vn.cmax.cafe.api.models.InfoResponse;
import vn.cmax.cafe.api.models.SocialMedia;
import vn.cmax.cafe.configuration.model.CmaxConfigurationProperties;
import vn.cmax.cafe.configuration.model.CmaxPublicInformation;

@javax.annotation.Generated(
    value = "io.swagger.codegen.v3.generators.java.SpringCodegen",
    date = "2023-09-18T23:03:51.040473+07:00[Asia/Ho_Chi_Minh]")
@RestController
public class InformationApiController implements InformationApi {

  private static final Logger log = LoggerFactory.getLogger(InformationApiController.class);

  private final CmaxConfigurationProperties cmaxConfigurationProperties;

  @org.springframework.beans.factory.annotation.Autowired
  public InformationApiController(CmaxConfigurationProperties cmaxConfigurationProperties) {
    this.cmaxConfigurationProperties = cmaxConfigurationProperties;
  }

  public ResponseEntity<InfoResponse> informationGet() {
    InfoResponse response = new InfoResponse();
    CmaxPublicInformation cmaxPublicInformation = cmaxConfigurationProperties.getInfo();
    Optional.ofNullable(cmaxPublicInformation.getHotline())
        .map(item -> StringUtils.isBlank(item) ? StringUtils.EMPTY : item)
        .ifPresent(item -> response.setHotline(item));
    Optional.ofNullable(cmaxPublicInformation.getAddress())
        .map(item -> StringUtils.isBlank(item) ? StringUtils.EMPTY : item)
        .ifPresent(item -> response.setAddress(item));
    SocialMedia socialMedia = new SocialMedia();
    Optional.ofNullable(cmaxPublicInformation.getFacebook())
        .map(item -> StringUtils.isBlank(item) ? StringUtils.EMPTY : item)
        .ifPresent(item -> socialMedia.setFacebook(item));
    Optional.ofNullable(cmaxPublicInformation.getTiktok())
        .map(item -> StringUtils.isBlank(item) ? StringUtils.EMPTY : item)
        .ifPresent(item -> socialMedia.setTiktok(item));
    Optional.ofNullable(cmaxPublicInformation.getInstagram())
        .map(item -> StringUtils.isBlank(item) ? StringUtils.EMPTY : item)
        .ifPresent(item -> socialMedia.setInstagram(item));
    response.setSocialMedia(socialMedia);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
