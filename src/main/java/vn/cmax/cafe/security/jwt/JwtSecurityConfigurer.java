package vn.cmax.cafe.security.jwt;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtSecurityConfigurer
    extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
  private final JwtTokenManager jwtTokenManager;

  public JwtSecurityConfigurer(JwtTokenManager jwtTokenManager) {
    this.jwtTokenManager = jwtTokenManager;
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    JwtTokenAuthenticationFilter customFilter = new JwtTokenAuthenticationFilter(jwtTokenManager);
    http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
  }
}
