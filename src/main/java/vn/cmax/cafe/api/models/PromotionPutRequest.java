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
 * PromotionPutRequest
 */
@Validated


public class PromotionPutRequest  implements Serializable  {
  private static final long serialVersionUID = 1L;

  @JsonProperty("title")
  private String title = null;

  @JsonProperty("content")
  private String content = null;

  @JsonProperty("subtitle")
  private String subtitle = null;

  @JsonProperty("image")
  private String image = null;

  public PromotionPutRequest title(String title) {
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

  public PromotionPutRequest content(String content) {
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

  public PromotionPutRequest subtitle(String subtitle) {
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

  public PromotionPutRequest image(String image) {
    this.image = image;
    return this;
  }

  /**
   * Get image
   * @return image
   **/
  @Schema(description = "")
  
    public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PromotionPutRequest promotionPutRequest = (PromotionPutRequest) o;
    return Objects.equals(this.title, promotionPutRequest.title) &&
        Objects.equals(this.content, promotionPutRequest.content) &&
        Objects.equals(this.subtitle, promotionPutRequest.subtitle) &&
        Objects.equals(this.image, promotionPutRequest.image);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, content, subtitle, image);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PromotionPutRequest {\n");
    
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    content: ").append(toIndentedString(content)).append("\n");
    sb.append("    subtitle: ").append(toIndentedString(subtitle)).append("\n");
    sb.append("    image: ").append(toIndentedString(image)).append("\n");
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
