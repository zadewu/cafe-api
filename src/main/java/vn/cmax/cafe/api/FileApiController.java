package vn.cmax.cafe.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import vn.cmax.cafe.api.models.FileUploadResponse;
import vn.cmax.cafe.configuration.model.CmaxConfigurationProperties;
import vn.cmax.cafe.exception.ApiErrors;
import vn.cmax.cafe.exception.CmaxException;
import vn.cmax.cafe.file.FileService;

@javax.annotation.Generated(
    value = "io.swagger.codegen.v3.generators.java.SpringCodegen",
    date = "2023-09-07T19:51:51.992732+07:00[Asia/Ho_Chi_Minh]")
@RestController
@Slf4j
public class FileApiController implements FileApi {
  private final ObjectMapper objectMapper;

  private final HttpServletRequest request;
  private final FileService fileService;
  private final CmaxConfigurationProperties configurationProperties;

  @org.springframework.beans.factory.annotation.Autowired
  public FileApiController(
      ObjectMapper objectMapper,
      HttpServletRequest request,
      FileService fileService,
      CmaxConfigurationProperties configurationProperties) {
    this.objectMapper = objectMapper;
    this.request = request;
    this.fileService = fileService;
    this.configurationProperties = configurationProperties;
  }

  @PreAuthorize("hasRole('ADMIN') or hasRole('AUDITOR')")
  public ResponseEntity<FileUploadResponse> fileUploadPost(
      @Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema())
          @RequestParam(value = "file", required = false)
          MultipartFile file) {
    String fileName = null;
    try {
      fileName = this.fileService.uploadFile(file);
    } catch (CmaxException e) {
      return ApiErrors.of(e);
    }
    if (StringUtils.isBlank(fileName)) {
      return new ResponseEntity("Error when upload file", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    String domain =
        StringUtils.isBlank(configurationProperties.getDomainName())
            ? "localhost"
            : configurationProperties.getDomainName();
    String remoteFileLocation = domain + fileName;
    FileUploadResponse response = new FileUploadResponse();
    response.setUrl(remoteFileLocation.replaceAll("\\s", ""));
    return new ResponseEntity<FileUploadResponse>(response, HttpStatus.CREATED);
  }
}
