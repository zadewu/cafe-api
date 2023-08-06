package vn.cmax.cafe.security.jwt.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Token {
  private final String jwtToken;
  private final String fingerprintToken;
}
