package vn.cmax.cafe.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ValueMapping;
import org.mapstruct.factory.Mappers;
import vn.cmax.cafe.api.models.Category;
import vn.cmax.cafe.category.MovieCategoryEntity;

import java.util.List;

@Mapper
public interface CategoryMapper {
    public static final CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    @Mapping(source = "categoryName", target = "name")
    Category fromEntity(MovieCategoryEntity entity);

    @Mapping(source = "name", target = "categoryName")
    MovieCategoryEntity fromCategory(Category category);

    List<Category> fromEntities(List<MovieCategoryEntity> entity);

    List<MovieCategoryEntity> fromCategories(List<Category> category);
}
