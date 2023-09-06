package vn.cmax.cafe.category;

import java.util.ArrayList;
import java.util.Optional;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import vn.cmax.cafe.api.models.Category;
import vn.cmax.cafe.api.models.CategoryPostRequest;
import vn.cmax.cafe.api.models.CategorySearchResponse;
import vn.cmax.cafe.exception.CmaxException;
import vn.cmax.cafe.mapper.CategoryMapper;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MovieCategoryService {
    private MovieCategoryRepository categoryRepository;

    public CategorySearchResponse findAll(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<MovieCategoryEntity> categoryEntities = categoryRepository.findAll(pageable);
        CategorySearchResponse response = new CategorySearchResponse().records(new ArrayList<>());
        Page<Category> categories = categoryEntities.map(item -> CategoryMapper.INSTANCE.fromEntity(item));
        response.records(categories.getContent())
                .pageSize(categories.getSize())
                .totalPage(categories.getTotalPages())
                .totalCount(categories.getTotalElements())
                .currentPage(categories.getNumber());
        return response;
    }

    public Category findById(Long id) {
        Optional<MovieCategoryEntity> entityOptional = this.categoryRepository.findById(id);
        if (entityOptional.isPresent()) {
            return CategoryMapper.INSTANCE.fromEntity(entityOptional.get());
        }
        return null;
    }

    public Categroy createNewCategory(CategoryPostRequest request) {

    }
}
