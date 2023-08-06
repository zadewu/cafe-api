package vn.cmax.cafe.security.jwt.model;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@ToString
public class CacheToken {
  private String accessToken;
  private String refreshToken;
}
