package vn.cmax.cafe.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.cmax.cafe.api.models.Movie;
import vn.cmax.cafe.api.models.MoviePostRequest;
import vn.cmax.cafe.api.models.MoviePutRequest;
import vn.cmax.cafe.api.models.MovieSearchResponse;
import vn.cmax.cafe.exception.ApiErrors;
import vn.cmax.cafe.exception.CmaxException;
import vn.cmax.cafe.movie.MovieService;

@javax.annotation.Generated(
    value = "io.swagger.codegen.v3.generators.java.SpringCodegen",
    date = "2023-09-06T22:55:26.603189+07:00[Asia/Ho_Chi_Minh]")
@RestController
@Slf4j
public class MovieApiController implements MovieApi {
  private final ObjectMapper objectMapper;

  private final HttpServletRequest request;

  private final MovieService movieService;

  @org.springframework.beans.factory.annotation.Autowired
  public MovieApiController(
      ObjectMapper objectMapper, HttpServletRequest request, MovieService movieService) {
    this.objectMapper = objectMapper;
    this.request = request;
    this.movieService = movieService;
  }

  public ResponseEntity<MovieSearchResponse> movieGet(
      @Min(0)
          @Parameter(
              in = ParameterIn.QUERY,
              description = "",
              schema = @Schema(allowableValues = {}))
          @Valid
          @RequestParam(value = "page", required = false)
          Integer page,
      @Min(10)
          @Max(50)
          @Parameter(
              in = ParameterIn.QUERY,
              description = "",
              schema =
                  @Schema(
                      allowableValues = {},
                      minimum = "10",
                      maximum = "50"))
          @Valid
          @RequestParam(value = "pageSize", required = false)
          Integer pageSize,
      @Parameter(in = ParameterIn.QUERY, description = "", schema = @Schema())
          @Valid
          @RequestParam(value = "category", required = false)
          Long category,
      @Parameter(in = ParameterIn.QUERY, description = "", schema = @Schema())
      @Valid
      @RequestParam(value = "keyWord", required = false)
      String keyword) {
    MovieSearchResponse response = this.movieService.findAllMovies(page, pageSize, category, keyword);
    return new ResponseEntity<MovieSearchResponse>(response, HttpStatus.OK);
  }

  @PreAuthorize("hasRole('ADMIN') or hasRole('AUDITOR')")
  @Override
  public ResponseEntity<Void> movieIdDelete(Long id) {
    try {
      this.movieService.deleteMovie(id);
      return new ResponseEntity(HttpStatus.NO_CONTENT);
    } catch (CmaxException e) {
      return ApiErrors.of(e);
    }
  }

  public ResponseEntity<Movie> movieIdGet(
      @Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema())
          @PathVariable("id")
          Long id) {
    try {
      Movie movie = this.movieService.findMovieById(id);
      return new ResponseEntity<Movie>(movie, HttpStatus.OK);
    } catch (CmaxException ex) {
      return ApiErrors.of(ex);
    }
  }

  @PreAuthorize("hasRole('ADMIN') or hasRole('AUDITOR')")
  public ResponseEntity<Void> movieIdPut(
      @Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema())
          @PathVariable("id")
          Long id,
      @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema())
          @Valid
          @RequestBody
          MoviePutRequest body) {
    try {
      this.movieService.updateMovie(id, body);
      return new ResponseEntity<>(HttpStatus.ACCEPTED);
    } catch (CmaxException ex) {
      return ApiErrors.of(ex);
    }
  }

  @PreAuthorize("hasRole('ADMIN') or hasRole('AUDITOR')")
  public ResponseEntity<Void> moviePost(
      @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema())
          @Valid
          @RequestBody
          MoviePostRequest body) {
    try {
      this.movieService.createMovie(body);
      return new ResponseEntity<>(HttpStatus.CREATED);
    } catch (CmaxException e) {
      return ApiErrors.of(e);
    }
  }
}
