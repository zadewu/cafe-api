package vn.cmax.cafe.movie;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import vn.cmax.cafe.api.models.Movie;
import vn.cmax.cafe.api.models.MoviePostRequest;
import vn.cmax.cafe.api.models.MoviePutRequest;
import vn.cmax.cafe.api.models.MovieSearchResponse;
import vn.cmax.cafe.category.MovieCategoryEntity;
import vn.cmax.cafe.category.MovieCategoryRepository;
import vn.cmax.cafe.exception.CmaxException;
import vn.cmax.cafe.exception.ValidationException;
import vn.cmax.cafe.mapper.MovieMapper;
import vn.cmax.cafe.utils.QuerySpecifications;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MovieService {
  private MovieRepository movieRepository;
  private MovieCategoryRepository categoryRepository;

  public MovieSearchResponse findAllMovies(
      int page, int pageSize, Long categoryId, String keyword) {
    Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "createdDate"));
    MovieSearchResponse response = new MovieSearchResponse().records(new ArrayList<>());
    Specification<MovieEntity> movieEntitySpecification =
        QuerySpecifications.searchMovie(categoryId, keyword);
    Page<MovieEntity> movieEntities =
        this.movieRepository.findAll(movieEntitySpecification, pageable);
    Page<Movie> movies = movieEntities.map(item -> MovieMapper.INSTANCE.fromEntity(item));
    response
        .records(movies.getContent())
        .pageSize(movies.getSize())
        .totalPage(movies.getTotalPages())
        .totalCount(movies.getTotalElements())
        .currentPage(movies.getNumber());
    return response;
  }

  public Movie findMovieById(Long movieId) throws CmaxException {
    Optional<MovieEntity> movieEntityOptional = this.movieRepository.findById(movieId);
    if (movieEntityOptional.isEmpty()) {
      throw new CmaxException("No movie with id [" + movieId + "] founded", HttpStatus.NOT_FOUND);
    }
    return movieEntityOptional.map(MovieMapper.INSTANCE::fromEntity).orElse(null);
  }

  @Transactional
  public void updateMovie(Long movieId, MoviePutRequest moviePutRequest)
      throws ValidationException {
    Optional<MovieEntity> movieEntityOptional = this.movieRepository.findById(movieId);
    if (movieEntityOptional.isEmpty()) {
      throw new ValidationException("No movie with Id " + movieId);
    }
    MovieEntity entity = movieEntityOptional.get();
    Optional.ofNullable(moviePutRequest.getName()).ifPresent(item -> entity.setMovieName(item));
    Optional.ofNullable(moviePutRequest.getDuration()).ifPresent(item -> entity.setDuration(item));
    Optional.ofNullable(moviePutRequest.getDescription())
        .ifPresent(item -> entity.setDescription(item));
    Optional.ofNullable(moviePutRequest.getTrailerLink())
        .ifPresent(item -> entity.setTrailerLink(item));
    Optional.ofNullable(moviePutRequest.getMovieImage())
        .ifPresent(item -> entity.setMovieImage(item));
    if (moviePutRequest.getCategory() != null) {
      Optional<MovieCategoryEntity> categoryEntityOptional =
          this.categoryRepository.findById(moviePutRequest.getCategory());
      if (categoryEntityOptional.isEmpty()) {
        throw new ValidationException(
            "Cannot assign movie to category with id = ["
                + moviePutRequest.getCategory()
                + "]. This category is not existed");
      }
      entity.setCategory(categoryEntityOptional.get());
    }
    this.movieRepository.save(entity);
  }

  @Transactional
  public Movie createMovie(MoviePostRequest request) throws ValidationException {
    try {
      Objects.requireNonNull(request.getName());
      Objects.requireNonNull(request.getDuration());
      Objects.requireNonNull(request.getDescription());
      Objects.requireNonNull(request.getCategory());
      Objects.requireNonNull(request.getTrailerLink());
      Objects.requireNonNull(request.getMovieImage());
    } catch (NullPointerException ex) {
      throw new ValidationException("Request body contains some blank fields", ex);
    }
    MovieEntity movieEntity = new MovieEntity();
    movieEntity.setMovieName(request.getName());
    movieEntity.setDuration(request.getDuration());
    movieEntity.setDescription(request.getDescription());
    movieEntity.setTrailerLink(request.getTrailerLink());
    movieEntity.setMovieImage(request.getMovieImage());
    Optional<MovieCategoryEntity> categoryEntityOptional =
        this.categoryRepository.findById(request.getCategory());
    if (categoryEntityOptional.isEmpty()) {
      throw new ValidationException(
          "Cannot assign movie to category with id = ["
              + request.getCategory()
              + "]. This category is not existed");
    }
    MovieCategoryEntity categoryEntity = categoryEntityOptional.get();
    movieEntity.setCategory(categoryEntity);
    MovieEntity saved = this.movieRepository.save(movieEntity);
    return MovieMapper.INSTANCE.fromEntity(saved);
  }

  @Transactional
  public void deleteMovie(Long id) throws CmaxException {
    Optional<MovieEntity> movieOpt = this.movieRepository.findById(id);
    if (movieOpt.isEmpty()) {
      throw new ValidationException("No movie with Id " + id);
    }
    MovieEntity movieEntity = movieOpt.get();
    this.movieRepository.delete(movieEntity);
  }
}
