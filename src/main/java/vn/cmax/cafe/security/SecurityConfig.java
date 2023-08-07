package vn.cmax.cafe.security;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import vn.cmax.cafe.security.jwt.JwtSecurityConfigurer;
import vn.cmax.cafe.security.jwt.JwtTokenManager;
import vn.cmax.cafe.user.UserService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtTokenManager jwtTokenManager;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.httpBasic()
        .disable()
        .csrf()
        .disable()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .antMatchers("/auth/login/**", "/auth/refresh**", "/users/signup**")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .apply(new JwtSecurityConfigurer(jwtTokenManager));
    http.cors();
    return http.build();
  }

  @Bean
  public DefaultUserDetailService userDetailService(UserService userService) {
    return new DefaultUserDetailService(userService);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }
}