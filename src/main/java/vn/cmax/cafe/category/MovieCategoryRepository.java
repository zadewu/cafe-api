package vn.cmax.cafe.category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieCategoryRepository extends JpaRepository<MovieCategoryEntity, Long> {
    Optional<MovieCategoryEntity> findMovieCategoryEntitiesByCategoryName(String name);
    Page<MovieCategoryEntity> findAll(Pageable pageable);
}
