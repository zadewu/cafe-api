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
 * MoviePutRequest
 */
@Validated


public class MoviePutRequest  implements Serializable  {
  private static final long serialVersionUID = 1L;

  @JsonProperty("name")
  private String name = null;

  @JsonProperty("duration")
  private Integer duration = null;

  @JsonProperty("description")
  private String description = null;

  @JsonProperty("trailerLink")
  private String trailerLink = null;

  @JsonProperty("category")
  private Long category = null;

  public MoviePutRequest name(String name) {
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

  public MoviePutRequest duration(Integer duration) {
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

  public MoviePutRequest description(String description) {
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

  public MoviePutRequest trailerLink(String trailerLink) {
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

  public MoviePutRequest category(Long category) {
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
    MoviePutRequest moviePutRequest = (MoviePutRequest) o;
    return Objects.equals(this.name, moviePutRequest.name) &&
        Objects.equals(this.duration, moviePutRequest.duration) &&
        Objects.equals(this.description, moviePutRequest.description) &&
        Objects.equals(this.trailerLink, moviePutRequest.trailerLink) &&
        Objects.equals(this.category, moviePutRequest.category);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, duration, description, trailerLink, category);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MoviePutRequest {\n");
    
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    duration: ").append(toIndentedString(duration)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    trailerLink: ").append(toIndentedString(trailerLink)).append("\n");
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
