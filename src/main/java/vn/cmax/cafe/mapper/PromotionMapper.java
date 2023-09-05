package vn.cmax.cafe.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.cmax.cafe.api.models.Promotion;
import vn.cmax.cafe.promotion.PromotionEntity;

@Mapper
public interface PromotionMapper {
    public static final PromotionMapper INSTANCE = Mappers.getMapper(PromotionMapper.class);

    public Promotion fromEntity(PromotionEntity entity);

    public PromotionEntity fromPromotion(Promotion promotion);
}
