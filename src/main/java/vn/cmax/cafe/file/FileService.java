package vn.cmax.cafe.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Year;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import vn.cmax.cafe.configuration.model.CmaxConfigurationProperties;
import vn.cmax.cafe.exception.ValidationException;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class FileService {
  private CmaxConfigurationProperties configurationProperties;

  public String uploadFile(MultipartFile multipartFile) {
    if (multipartFile.isEmpty()) {
      throw new ValidationException("File content is empty");
    }
    try {
      Path filePath = getFileDirectory();
      String originalFileName = multipartFile.getOriginalFilename();
      String fileExtension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
      String filename = UUID.randomUUID().toString();
      String finalName = filename + "." + fileExtension;
      Path finalFilePath = filePath.resolve(finalName);
      Files.write(finalFilePath, multipartFile.getBytes());
      return finalFilePath.toString();
    } catch (IOException ex) {
      log.error("Error while saving file", ex);
    }
    return StringUtils.EMPTY;
  }

  private Path getFileDirectory() throws IOException {
    if (StringUtils.isBlank(configurationProperties.getFileDirectory())) {
      throw new IllegalArgumentException("Cannot find files directory");
    }
    int currentYear = Year.now().getValue();
    String filePath = configurationProperties.getFileDirectory();
    Path currentYearPath = Paths.get(filePath, String.valueOf(currentYear));
    if (!Files.exists(currentYearPath)) {
      Files.createDirectories(currentYearPath);
    }
    return currentYearPath;
  }
}
