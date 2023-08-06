package vn.cmax.cafe.configuration.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SecurityProperties {
  private long nonceTimeout;
  private JwtProperties jwt;
  private String cookiePath;
  private boolean cookieSecure;
}
