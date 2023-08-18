package vn.cmax.cafe.movie;

import lombok.*;
import org.springframework.validation.annotation.Validated;
import vn.cmax.cafe.category.MovieCategoryEntity;
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
@Table(name = "movie")
public class MovieEntity extends DomainAudit implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name", unique = true)
    private String movieName;

    @Column(name = "duration")
    private String duration;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "fk_movie_category"))
    private MovieCategoryEntity category;
}
