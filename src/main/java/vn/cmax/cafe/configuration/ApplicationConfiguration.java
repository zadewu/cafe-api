package vn.cmax.cafe.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import vn.cmax.cafe.configuration.model.CmaxConfigurationProperties;
import vn.cmax.cafe.configuration.model.SecurityProperties;

@Configuration
public class ApplicationConfiguration {
  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

  @Bean
  @ConfigurationProperties(value = "security")
  public SecurityProperties securityProperties() {
    return new SecurityProperties();
  }

  @Bean
  @ConfigurationProperties(value = "cmax")
  public CmaxConfigurationProperties cmaxConfigurationProperties() {
    return new CmaxConfigurationProperties();
  }
}
