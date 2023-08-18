package vn.cmax.cafe.movie;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<MovieEntity, Long> {
    List<MovieEntity> findByMovieNameContainsIgnoreCase(String query);

    List<MovieEntity> findByCategoryId(Long id);
}
