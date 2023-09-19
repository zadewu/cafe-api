package vn.cmax.cafe.promotion;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PromotionRepository extends JpaRepository<PromotionEntity, Long> {
    Page<PromotionEntity> findAllByOrderByUpdatedDateDesc(Pageable pageable);
}
