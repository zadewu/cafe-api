package vn.cmax.cafe.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
  @Bean
  public CorsFilter corsFilter() {
    CorsConfiguration corsConfig = new CorsConfiguration();
    corsConfig.setAllowCredentials(true);
    corsConfig.addAllowedOrigin("http://localhost:4200"); // Allow requests from http://localhost
    corsConfig.addAllowedOrigin("https://cmax-cafe.web.app");
    corsConfig.addAllowedHeader("*"); // You can customize allowed headers here
    corsConfig.addAllowedMethod("*"); // You can customize allowed HTTP methods here

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfig); // Apply CORS settings to all endpoints

    return new CorsFilter(source);
  }
}
