package vn.cmax.cafe.api.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import vn.cmax.cafe.api.models.Movie;
import vn.cmax.cafe.api.models.SearchResponse;
import java.io.Serializable;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * MovieSearchResponse
 */
@Validated


public class MovieSearchResponse extends SearchResponse implements Serializable  {
  private static final long serialVersionUID = 1L;

  @JsonProperty("records")
  @Valid
  private List<Movie> records = null;

  public MovieSearchResponse records(List<Movie> records) {
    this.records = records;
    return this;
  }

  public MovieSearchResponse addRecordsItem(Movie recordsItem) {
    if (this.records == null) {
      this.records = new ArrayList<Movie>();
    }
    this.records.add(recordsItem);
    return this;
  }

  /**
   * Get records
   * @return records
   **/
  @Schema(description = "")
      @Valid
    public List<Movie> getRecords() {
    return records;
  }

  public void setRecords(List<Movie> records) {
    this.records = records;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MovieSearchResponse movieSearchResponse = (MovieSearchResponse) o;
    return Objects.equals(this.records, movieSearchResponse.records) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(records, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MovieSearchResponse {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    records: ").append(toIndentedString(records)).append("\n");
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
