package vn.cmax.cafe.category;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import lombok.*;
import org.springframework.validation.annotation.Validated;
import vn.cmax.cafe.movie.MovieEntity;
import vn.cmax.cafe.support.DomainAudit;

@Validated
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movie_category")
public class MovieCategoryEntity extends DomainAudit implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name", unique = true)
    private String categoryName;

  @OneToMany(
      mappedBy = "category",
      cascade = CascadeType.ALL,
      fetch = FetchType.LAZY,
      orphanRemoval = true)
  private List<MovieEntity> movies = new ArrayList<>();
}
