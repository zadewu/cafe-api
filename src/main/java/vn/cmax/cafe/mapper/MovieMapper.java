package vn.cmax.cafe.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import vn.cmax.cafe.api.models.Movie;
import vn.cmax.cafe.movie.MovieEntity;

@Mapper
public interface MovieMapper {
    public static final MovieMapper INSTANCE = Mappers.getMapper(MovieMapper.class);

    Movie fromEntity(MovieEntity movieEntity);

    MovieEntity fromMovie(Movie movie);
}
