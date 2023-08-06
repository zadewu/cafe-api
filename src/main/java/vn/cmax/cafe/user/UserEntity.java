package vn.cmax.cafe.user;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import vn.cmax.cafe.security.jwt.JwtTokenEntity;
import vn.cmax.cafe.support.DomainAudit;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Validated
@Getter
@Setter
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class UserEntity extends DomainAudit implements Serializable, UserDetails {
  private static final long serialVersionUID = 1L;
  private static final String ROLE_PREFIX = "ROLE_";

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  @Column(name = "username", unique = true)
  private String username;

  @Column(name = "email", nullable = false)
  private String email;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "firstname", nullable = false)
  private String firstName;

  @Column(name = "lastname", nullable = false)
  private String lastName;

  @Column(name = "roles", nullable = false)
  @Enumerated(EnumType.STRING)
  @ElementCollection(targetClass = UserRole.class, fetch = FetchType.EAGER)
  private Set<UserRole> roles;

  @Column(name = "is_activated", nullable = false)
  @Builder.Default
  private Boolean isActivate = false;

  @Column(name = "otp", length = 4)
  private Integer otp;

  @Column(name = "nonce", length = 100)
  private String nonce;

  @Column(name = "deleted", columnDefinition = "boolean default false")
  @Builder.Default
  private Boolean deleted = false;

  @OneToMany(
      mappedBy = "user",
      cascade = CascadeType.ALL,
      fetch = FetchType.EAGER,
      orphanRemoval = true)
  @OrderBy("updatedDate ASC")
  @Builder.Default
  private List<JwtTokenEntity> jwtTokens = new ArrayList<>();

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.getRoles().stream()
        .map(role -> new SimpleGrantedAuthority(ROLE_PREFIX + role.name()))
        .collect(Collectors.toList());
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return isActivate;
  }

  public void addToken(JwtTokenEntity jwtTokenEntity) {
    if (this.jwtTokens == null) this.jwtTokens = new ArrayList<>();

    this.jwtTokens.add(jwtTokenEntity);
    jwtTokenEntity.setUser(this);
  }

  public void removeToken(JwtTokenEntity tokenEntity) {
    if (this.jwtTokens == null) {
      return;
    }
    jwtTokens.remove(tokenEntity);
    tokenEntity.setUser(null);
  }
}
