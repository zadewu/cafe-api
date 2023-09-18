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
 * SocialMedia
 */
@Validated


public class SocialMedia  implements Serializable  {
  private static final long serialVersionUID = 1L;

  @JsonProperty("facebook")
  private String facebook = null;

  @JsonProperty("instagram")
  private String instagram = null;

  @JsonProperty("tiktok")
  private String tiktok = null;

  public SocialMedia facebook(String facebook) {
    this.facebook = facebook;
    return this;
  }

  /**
   * Get facebook
   * @return facebook
   **/
  @Schema(description = "")
  
    public String getFacebook() {
    return facebook;
  }

  public void setFacebook(String facebook) {
    this.facebook = facebook;
  }

  public SocialMedia instagram(String instagram) {
    this.instagram = instagram;
    return this;
  }

  /**
   * Get instagram
   * @return instagram
   **/
  @Schema(description = "")
  
    public String getInstagram() {
    return instagram;
  }

  public void setInstagram(String instagram) {
    this.instagram = instagram;
  }

  public SocialMedia tiktok(String tiktok) {
    this.tiktok = tiktok;
    return this;
  }

  /**
   * Get tiktok
   * @return tiktok
   **/
  @Schema(description = "")
  
    public String getTiktok() {
    return tiktok;
  }

  public void setTiktok(String tiktok) {
    this.tiktok = tiktok;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SocialMedia socialMedia = (SocialMedia) o;
    return Objects.equals(this.facebook, socialMedia.facebook) &&
        Objects.equals(this.instagram, socialMedia.instagram) &&
        Objects.equals(this.tiktok, socialMedia.tiktok);
  }

  @Override
  public int hashCode() {
    return Objects.hash(facebook, instagram, tiktok);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SocialMedia {\n");
    
    sb.append("    facebook: ").append(toIndentedString(facebook)).append("\n");
    sb.append("    instagram: ").append(toIndentedString(instagram)).append("\n");
    sb.append("    tiktok: ").append(toIndentedString(tiktok)).append("\n");
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
