package vn.cmax.cafe.security.jwt;

import lombok.*;
import vn.cmax.cafe.support.DomainAudit;
import vn.cmax.cafe.user.UserEntity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "jwt_token")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class JwtTokenEntity extends DomainAudit implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  @Column(name = "access_token", length = 512)
  private String accessToken;

  @Column(name = "refresh_token", length = 512)
  private String refreshToken;

  @ManyToOne
  @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_token"))
  private UserEntity user;
}
