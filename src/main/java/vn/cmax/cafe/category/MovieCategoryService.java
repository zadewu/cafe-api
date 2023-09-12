package vn.cmax.cafe.category;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import vn.cmax.cafe.api.models.Category;
import vn.cmax.cafe.api.models.CategoryPostRequest;
import vn.cmax.cafe.api.models.CategoryPutRequest;
import vn.cmax.cafe.api.models.CategorySearchResponse;
import vn.cmax.cafe.exception.CmaxException;
import vn.cmax.cafe.exception.ValidationException;
import vn.cmax.cafe.mapper.CategoryMapper;
import vn.cmax.cafe.movie.MovieEntity;
import vn.cmax.cafe.movie.MovieRepository;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MovieCategoryService {
  private MovieCategoryRepository categoryRepository;
  private MovieRepository movieRepository;

  public CategorySearchResponse findAll(int page, int pageSize) {
    Pageable pageable = PageRequest.of(page, pageSize);
    Page<MovieCategoryEntity> categoryEntities = categoryRepository.findAll(pageable);
    CategorySearchResponse response = new CategorySearchResponse().records(new ArrayList<>());
    Page<Category> categories =
        categoryEntities.map(item -> CategoryMapper.INSTANCE.fromEntity(item));
    response
        .records(categories.getContent())
        .pageSize(categories.getSize())
        .totalPage(categories.getTotalPages())
        .totalCount(categories.getTotalElements())
        .currentPage(categories.getNumber());
    return response;
  }

  public Category findById(Long id) throws CmaxException {
    Optional<MovieCategoryEntity> entityOptional = this.categoryRepository.findById(id);
    if (entityOptional.isEmpty()) {
      throw new ValidationException("Cannot find category with id = " + id);
    }
    return CategoryMapper.INSTANCE.fromEntity(entityOptional.get());
  }

  @Transactional
  public Category createNewCategory(CategoryPostRequest request) throws CmaxException {
    try {
      Objects.requireNonNull(request.getName());
    } catch (NullPointerException ex) {
      throw new ValidationException("Request body contains some blank fields", ex);
    }
    MovieCategoryEntity categoryEntity = new MovieCategoryEntity();
    categoryEntity.setCategoryName(request.getName());
    MovieCategoryEntity saved = categoryRepository.save(categoryEntity);
    return CategoryMapper.INSTANCE.fromEntity(saved);
  }

  @Transactional
  public void updateCategory(Long id, CategoryPutRequest request) throws CmaxException {
    try {
      Objects.requireNonNull(request.getName());
    } catch (NullPointerException ex) {
      throw new ValidationException("Request body contains some blank fields", ex);
    }
    Optional<MovieCategoryEntity> foundedOpt = this.categoryRepository.findById(id);
    if (foundedOpt.isEmpty()) {
      throw new CmaxException("No category with id [" + id + "] founded", HttpStatus.NOT_FOUND);
    }
    MovieCategoryEntity founded = foundedOpt.get();
    founded.setCategoryName(request.getName());
    this.categoryRepository.save(founded);
  }

  @Transactional
  public void deleteCategory(Long id) throws CmaxException {
    Optional<MovieCategoryEntity> foundedOpt = this.categoryRepository.findById(id);
    if (foundedOpt.isEmpty()) {
      throw new CmaxException("No category with id [" + id + "] founded", HttpStatus.NOT_FOUND);
    }
    MovieCategoryEntity founded = foundedOpt.get();
    if (StringUtils.equalsIgnoreCase(founded.getCategoryName(), "Other")) {
      throw new CmaxException(
          "This is default category. Cannot be deleted", HttpStatus.METHOD_NOT_ALLOWED);
    }
    Optional<MovieCategoryEntity> otherCategoryOpt = this.categoryRepository.findById(100L);
    if (otherCategoryOpt.isEmpty()) {
      throw new CmaxException(
          "Default category name 'Other' is not founded ", HttpStatus.NOT_FOUND);
    }
    MovieCategoryEntity otherCategory = otherCategoryOpt.get();
    List<MovieEntity> moviesOfCategory = founded.getMovies();
    moviesOfCategory.stream()
        .forEach(
            item -> {
              item.setCategory(otherCategory);
              this.movieRepository.save(item);
            });
  }
}
