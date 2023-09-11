package vn.cmax.cafe.promotion;

import lombok.*;
import org.springframework.validation.annotation.Validated;
import vn.cmax.cafe.support.DomainAudit;

import javax.persistence.*;
import java.io.Serializable;

@Validated
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "promotion")
public class PromotionEntity extends DomainAudit implements Serializable {
    private static final long serialVersionUid = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;
}
