package vn.cmax.cafe.support;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DomainAudit implements Serializable {
  private static final long serialVersionUID = 1L;

  @CreatedDate
  @Column(name = "created_date", nullable = false, updatable = false)
  private LocalDateTime createdDate;

  @LastModifiedDate
  @Column(name = "updated_date", nullable = false)
  private LocalDateTime updatedDate;

  @CreatedBy
  @Column(name = "created_by", nullable = false, updatable = false)
  private Long createdBy;

  @LastModifiedBy
  @Column(name = "updated_by", nullable = false)
  private Long updatedBy;
}
