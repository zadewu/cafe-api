package vn.cmax.cafe.configuration.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JwtProperties {
  private long accessTokenTimeout;
  private long refreshTokenTimeout;
  private String issuer;
  private int maxTokenPerUser = 10;
}
