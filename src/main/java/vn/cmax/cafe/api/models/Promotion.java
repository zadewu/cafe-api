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
 * Promotion
 */
@Validated


public class Promotion  implements Serializable  {
  private static final long serialVersionUID = 1L;

  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("title")
  private String title = null;

  @JsonProperty("subtitle")
  private String subtitle = null;

  @JsonProperty("content")
  private String content = null;

  public Promotion id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
   **/
  @Schema(description = "")
  
    public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Promotion title(String title) {
    this.title = title;
    return this;
  }

  /**
   * Get title
   * @return title
   **/
  @Schema(description = "")
  
    public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Promotion subtitle(String subtitle) {
    this.subtitle = subtitle;
    return this;
  }

  /**
   * Get subtitle
   * @return subtitle
   **/
  @Schema(description = "")
  
    public String getSubtitle() {
    return subtitle;
  }

  public void setSubtitle(String subtitle) {
    this.subtitle = subtitle;
  }

  public Promotion content(String content) {
    this.content = content;
    return this;
  }

  /**
   * Get content
   * @return content
   **/
  @Schema(description = "")
  
    public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Promotion promotion = (Promotion) o;
    return Objects.equals(this.id, promotion.id) &&
        Objects.equals(this.title, promotion.title) &&
        Objects.equals(this.subtitle, promotion.subtitle) &&
        Objects.equals(this.content, promotion.content);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, subtitle, content);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Promotion {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    subtitle: ").append(toIndentedString(subtitle)).append("\n");
    sb.append("    content: ").append(toIndentedString(content)).append("\n");
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
