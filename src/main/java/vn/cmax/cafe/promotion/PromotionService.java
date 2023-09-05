package vn.cmax.cafe.promotion;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import vn.cmax.cafe.api.models.*;
import vn.cmax.cafe.exception.CmaxException;
import vn.cmax.cafe.exception.ValidationException;
import vn.cmax.cafe.mapper.MovieMapper;
import vn.cmax.cafe.mapper.PromotionMapper;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PromotionService {
  private PromotionRepository promotionRepository;

  @Transactional
  public Promotion createNewPromotion(PromotionPostRequest request) {
    if (StringUtils.isBlank(request.getTitle())) {
      throw new ValidationException("Promotion title cannot be blank");
    }
    if (StringUtils.isBlank(request.getContent())) {
      throw new ValidationException("Promotion content cannot be blank");
    }
    PromotionEntity entity = new PromotionEntity();
    entity.setTitle(request.getTitle());
    entity.setContent(request.getContent());
    PromotionEntity saved = this.promotionRepository.save(entity);
    return PromotionMapper.INSTANCE.fromEntity(saved);
  }

  @Transactional
  public void updatePromotion(Long id, PromotionPutRequest request) {
    if (Objects.isNull(id)) {
      throw new ValidationException("Promotion ID is required for updating content");
    }
    Optional<PromotionEntity> promotionEntityOptional = this.promotionRepository.findById(id);
    if (promotionEntityOptional.isEmpty()) {
      throw new CmaxException("Cannot find promotion with id = " + id, HttpStatus.NOT_FOUND);
    }
    PromotionEntity saved = promotionEntityOptional.get();
    Optional.ofNullable(request.getTitle()).ifPresent(item -> saved.setTitle(item));
    Optional.ofNullable(request.getContent()).ifPresent(item -> saved.setContent(item));
    this.promotionRepository.save(saved);
  }

  public Promotion findPromotionWith(Long id) {
    if (Objects.isNull(id)) {
      throw new ValidationException("Promotion ID is required for updating content");
    }
    Optional<PromotionEntity> promotionEntityOptional = this.promotionRepository.findById(id);
    if (promotionEntityOptional.isEmpty()) {
      throw new CmaxException("Cannot find promotion with id = " + id, HttpStatus.NOT_FOUND);
    }
    PromotionEntity saved = promotionEntityOptional.get();
    return PromotionMapper.INSTANCE.fromEntity(saved);
  }

  public PromotionSearchResponse findAllPromotion(int page, int pageSize) {
    Pageable pageable = PageRequest.of(page, pageSize);
    PromotionSearchResponse response = new PromotionSearchResponse().records(new ArrayList<>());
    Page<PromotionEntity> promotionEntityPage = this.promotionRepository.findAll(pageable);
    Page<Promotion> promotions = promotionEntityPage.map(item -> PromotionMapper.INSTANCE.fromEntity(item));
    response.records(promotions.getContent())
            .pageSize(promotions.getSize())
            .totalPage(promotions.getTotalPages())
            .totalCount(promotions.getTotalElements())
            .currentPage(promotions.getNumber());
    return response;
  }
}