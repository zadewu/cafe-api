package vn.cmax.cafe.movie;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.cmax.cafe.category.MovieCategoryEntity;

import java.util.List;

public interface MovieRepository extends JpaRepository<MovieEntity, Long> {
    List<MovieEntity> findByCategoryId(Long id);

    Page<MovieEntity> findAllByOrderByCreatedDateDesc(Pageable pageable);

    Page<MovieEntity> findAllByCategoryIdOrderByCreatedDateDesc(Long id, Pageable pageable);

    Page<MovieEntity> findByMovieNameContainsIgnoreCaseOrderByCreatedDateDesc(String keyword, Pageable pageable);
}
