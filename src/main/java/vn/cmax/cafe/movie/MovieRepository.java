package vn.cmax.cafe.movie;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<MovieEntity, Long> {

  Page<MovieEntity> findAllByOrderByCreatedDateDesc(Pageable pageable);

  Page<MovieEntity> findAllByCategoryIdOrderByCreatedDateDesc(Long id, Pageable pageable);

  Page<MovieEntity> findByMovieNameContainsIgnoreCaseOrderByCreatedDateDesc(
      String keyword, Pageable pageable);

  Page<MovieEntity> findAll(Specification<MovieEntity> specification, Pageable pageable);
}
