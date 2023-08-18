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
 * SearchResponse
 */
@Validated


public class SearchResponse  implements Serializable  {
  private static final long serialVersionUID = 1L;

  @JsonProperty("currentPage")
  private Integer currentPage = null;

  @JsonProperty("pageSize")
  private Integer pageSize = null;

  @JsonProperty("totalCount")
  private Long totalCount = null;

  @JsonProperty("totalPage")
  private Integer totalPage = null;

  public SearchResponse currentPage(Integer currentPage) {
    this.currentPage = currentPage;
    return this;
  }

  /**
   * Get currentPage
   * minimum: 0
   * @return currentPage
   **/
  @Schema(description = "")
  
  @Min(0)  public Integer getCurrentPage() {
    return currentPage;
  }

  public void setCurrentPage(Integer currentPage) {
    this.currentPage = currentPage;
  }

  public SearchResponse pageSize(Integer pageSize) {
    this.pageSize = pageSize;
    return this;
  }

  /**
   * Get pageSize
   * minimum: 10
   * maximum: 50
   * @return pageSize
   **/
  @Schema(description = "")
  
  @Min(10) @Max(50)   public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public SearchResponse totalCount(Long totalCount) {
    this.totalCount = totalCount;
    return this;
  }

  /**
   * Get totalCount
   * minimum: 0
   * @return totalCount
   **/
  @Schema(description = "")
  
  @Min(0L)  public Long getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(Long totalCount) {
    this.totalCount = totalCount;
  }

  public SearchResponse totalPage(Integer totalPage) {
    this.totalPage = totalPage;
    return this;
  }

  /**
   * Get totalPage
   * minimum: 0
   * @return totalPage
   **/
  @Schema(description = "")
  
  @Min(0)  public Integer getTotalPage() {
    return totalPage;
  }

  public void setTotalPage(Integer totalPage) {
    this.totalPage = totalPage;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SearchResponse searchResponse = (SearchResponse) o;
    return Objects.equals(this.currentPage, searchResponse.currentPage) &&
        Objects.equals(this.pageSize, searchResponse.pageSize) &&
        Objects.equals(this.totalCount, searchResponse.totalCount) &&
        Objects.equals(this.totalPage, searchResponse.totalPage);
  }

  @Override
  public int hashCode() {
    return Objects.hash(currentPage, pageSize, totalCount, totalPage);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SearchResponse {\n");
    
    sb.append("    currentPage: ").append(toIndentedString(currentPage)).append("\n");
    sb.append("    pageSize: ").append(toIndentedString(pageSize)).append("\n");
    sb.append("    totalCount: ").append(toIndentedString(totalCount)).append("\n");
    sb.append("    totalPage: ").append(toIndentedString(totalPage)).append("\n");
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
