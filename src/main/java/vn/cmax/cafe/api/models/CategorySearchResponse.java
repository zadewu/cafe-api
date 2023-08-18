package vn.cmax.cafe.api.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import vn.cmax.cafe.api.models.Category;
import vn.cmax.cafe.api.models.SearchResponse;
import java.io.Serializable;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * CategorySearchResponse
 */
@Validated


public class CategorySearchResponse extends SearchResponse implements Serializable  {
  private static final long serialVersionUID = 1L;

  @JsonProperty("records")
  @Valid
  private List<Category> records = null;

  public CategorySearchResponse records(List<Category> records) {
    this.records = records;
    return this;
  }

  public CategorySearchResponse addRecordsItem(Category recordsItem) {
    if (this.records == null) {
      this.records = new ArrayList<Category>();
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
    public List<Category> getRecords() {
    return records;
  }

  public void setRecords(List<Category> records) {
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
    CategorySearchResponse categorySearchResponse = (CategorySearchResponse) o;
    return Objects.equals(this.records, categorySearchResponse.records) &&
        super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(records, super.hashCode());
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CategorySearchResponse {\n");
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
