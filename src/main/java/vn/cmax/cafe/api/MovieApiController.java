package vn.cmax.cafe.api;

import lombok.extern.slf4j.Slf4j;
import vn.cmax.cafe.api.models.ApiError;
import vn.cmax.cafe.api.models.Movie;
import vn.cmax.cafe.api.models.MoviePostRequest;
import vn.cmax.cafe.api.models.MoviePutRequest;
import vn.cmax.cafe.api.models.MovieSearchResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;import vn.cmax.cafe.movie.MovieService;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

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
  public MovieApiController(ObjectMapper objectMapper, HttpServletRequest request, MovieService movieService) {
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
        return new ResponseEntity<MovieSearchResponse>(response, HttpStatus.OK);
    }
    return new ResponseEntity<MovieSearchResponse>(HttpStatus.BAD_REQUEST);
  }

  public ResponseEntity<Movie> movieIdGet(
      @Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema())
          @PathVariable("id")
          Long id) {
    String accept = request.getHeader("Accept");
    if (accept != null && accept.contains("application/json")) {
      Movie movie = this.movieService.findMovieById(id);
      return new ResponseEntity<Movie>(movie, HttpStatus.OK);
    }
    return new ResponseEntity<Movie>(HttpStatus.BAD_REQUEST);
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
          return new ResponseEntity<>(HttpStatus.ACCEPTED);
      }
    return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
  }

  public ResponseEntity<Void> moviePost(
      @Parameter(in = ParameterIn.DEFAULT, description = "", required = true, schema = @Schema())
          @Valid
          @RequestBody
          MoviePostRequest body) {
    String accept = request.getHeader("Accept");
      if (accept != null && accept.contains("application/json")) {
          this.movieService.createMovie(body);
          return new ResponseEntity<>(HttpStatus.CREATED);
      }
      return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
  }
}
