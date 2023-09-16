package vn.cmax.cafe.mapper;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import vn.cmax.cafe.api.models.Movie;
import vn.cmax.cafe.movie.MovieEntity;

@Mapper(uses = {CategoryMapper.class}, builder = @Builder(disableBuilder = true))
public interface MovieMapper {
  public static final MovieMapper INSTANCE = Mappers.getMapper(MovieMapper.class);

  @Mapping(source = "movieName", target = "name")
  Movie fromEntity(MovieEntity movieEntity);

  @Mapping(source = "name", target = "movieName")
  MovieEntity fromMovie(Movie movie);
}
