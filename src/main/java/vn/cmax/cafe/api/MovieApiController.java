package vn.cmax.cafe.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.cmax.cafe.api.models.Movie;
import vn.cmax.cafe.api.models.MoviePostRequest;
import vn.cmax.cafe.api.models.MoviePutRequest;
import vn.cmax.cafe.api.models.MovieSearchResponse;
import vn.cmax.cafe.exception.CmaxException;
import vn.cmax.cafe.movie.MovieService;

@javax.annotation.Generated(
    value = "io.swagger.codegen.v3.generators.java.SpringCodegen",
    date = "2023-08-27T21:24:51.194362+07:00[Asia/Ho_Chi_Minh]")
@RestController
public class MovieApiController implements MovieApi {

  private static final Logger log = LoggerFactory.getLogger(MovieApiController.class);

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
          Long category) {
    String accept = request.getHeader("Accept");
    if (accept != null && accept.contains("application/json")) {
      MovieSearchResponse response = this.movieService.findAllMovies(page, pageSize, category);
      return new ResponseEntity<>(response, HttpStatus.OK);
    }
    return new ResponseEntity<>(new MovieSearchResponse(), HttpStatus.OK);
  }

  public ResponseEntity movieIdGet(
      @Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema())
          @PathVariable("id")
          Long id) {
    String accept = request.getHeader("Accept");
    if (accept != null && accept.contains("application/json")) {
      try {
        Movie movie = this.movieService.findMovieById(id);
        if (movie != null) {
          return new ResponseEntity<Movie>(movie, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
      } catch (CmaxException exception) {
        return new ResponseEntity(exception.getMessage(), exception.getStatus());
      }
    }

    return new ResponseEntity<Movie>(HttpStatus.NOT_IMPLEMENTED);
  }

  public ResponseEntity<Void> movieIdPut(
      @Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema())
          @PathVariable("id")
          Long id,
      @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema())
          @Valid
          @RequestBody
          MoviePutRequest body) {
    String accept = request.getHeader("Accept");
    if (accept != null && accept.contains("application/json")) {
      this.movieService.updateMovie(id, body);
      return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
    }
    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  }

  public ResponseEntity<Void> moviePost(
      @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema())
          @Valid
          @RequestBody
          MoviePostRequest body) {
    String accept = request.getHeader("Accept");
    if (accept != null && accept.contains("application/json")) {
      Movie movie = this.movieService.createMovie(body);
      if (movie != null) {
        return new ResponseEntity<>(HttpStatus.CREATED);
      }
    }
    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
