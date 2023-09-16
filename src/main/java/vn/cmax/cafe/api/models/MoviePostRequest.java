package vn.cmax.cafe.api.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * MoviePostRequest
 */
@Validated


public class MoviePostRequest  implements Serializable  {
  private static final long serialVersionUID = 1L;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("duration")
  private Integer duration = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("trailerLink")
  private String trailerLink = null;

  @JsonProperty("movieImage")
  private String movieImage = null;

  @JsonProperty("category")
  private Long category = null;

  public MoviePostRequest name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
   **/
  @Schema(description = "")
  
    public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public MoviePostRequest duration(Integer duration) {
    this.duration = duration;
    return this;
  }

  /**
   * Get duration
   * @return duration
   **/
  @Schema(description = "")
  
    public Integer getDuration() {
    return duration;
  }

  public void setDuration(Integer duration) {
    this.duration = duration;
  }

  public MoviePostRequest description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
   **/
  @Schema(description = "")
  
    public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public MoviePostRequest trailerLink(String trailerLink) {
    this.trailerLink = trailerLink;
    return this;
  }

  /**
   * Get trailerLink
   * @return trailerLink
   **/
  @Schema(description = "")
  
    public String getTrailerLink() {
    return trailerLink;
  }

  public void setTrailerLink(String trailerLink) {
    this.trailerLink = trailerLink;
  }

  public MoviePostRequest movieImage(String movieImage) {
    this.movieImage = movieImage;
    return this;
  }

  /**
   * Get movieImage
   * @return movieImage
   **/
  @Schema(description = "")
  
    public String getMovieImage() {
    return movieImage;
  }

  public void setMovieImage(String movieImage) {
    this.movieImage = movieImage;
  }

  public MoviePostRequest category(Long category) {
    this.category = category;
    return this;
  }

  /**
   * Get category
   * @return category
   **/
  @Schema(description = "")
  
    public Long getCategory() {
    return category;
  }

  public void setCategory(Long category) {
    this.category = category;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MoviePostRequest moviePostRequest = (MoviePostRequest) o;
    return Objects.equals(this.name, moviePostRequest.name) &&
        Objects.equals(this.duration, moviePostRequest.duration) &&
        Objects.equals(this.description, moviePostRequest.description) &&
        Objects.equals(this.trailerLink, moviePostRequest.trailerLink) &&
        Objects.equals(this.movieImage, moviePostRequest.movieImage) &&
        Objects.equals(this.category, moviePostRequest.category);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, duration, description, trailerLink, movieImage, category);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MoviePostRequest {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    duration: ").append(toIndentedString(duration)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    trailerLink: ").append(toIndentedString(trailerLink)).append("\n");
    sb.append("    movieImage: ").append(toIndentedString(movieImage)).append("\n");
    sb.append("    category: ").append(toIndentedString(category)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
