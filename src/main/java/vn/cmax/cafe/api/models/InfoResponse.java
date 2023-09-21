package vn.cmax.cafe.api.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import vn.cmax.cafe.api.models.SocialMedia;
import java.io.Serializable;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * InfoResponse
 */
@Validated


public class InfoResponse  implements Serializable  {
  private static final long serialVersionUID = 1L;

  @JsonProperty("hotline")
  private String hotline = null;

  @JsonProperty("address")
  private String address = null;

  @JsonProperty("socialMedia")
  private SocialMedia socialMedia = null;

  public InfoResponse hotline(String hotline) {
    this.hotline = hotline;
    return this;
  }

  /**
   * Get hotline
   * @return hotline
   **/
  @Schema(description = "")
  
    public String getHotline() {
    return hotline;
  }

  public void setHotline(String hotline) {
    this.hotline = hotline;
  }

  public InfoResponse address(String address) {
    this.address = address;
    return this;
  }

  /**
   * Get address
   * @return address
   **/
  @Schema(description = "")
  
    public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public InfoResponse socialMedia(SocialMedia socialMedia) {
    this.socialMedia = socialMedia;
    return this;
  }

  /**
   * Get socialMedia
   * @return socialMedia
   **/
  @Schema(description = "")
  
    @Valid
    public SocialMedia getSocialMedia() {
    return socialMedia;
  }

  public void setSocialMedia(SocialMedia socialMedia) {
    this.socialMedia = socialMedia;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InfoResponse infoResponse = (InfoResponse) o;
    return Objects.equals(this.hotline, infoResponse.hotline) &&
        Objects.equals(this.address, infoResponse.address) &&
        Objects.equals(this.socialMedia, infoResponse.socialMedia);
  }

  @Override
  public int hashCode() {
    return Objects.hash(hotline, address, socialMedia);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InfoResponse {\n");
    
    sb.append("    hotline: ").append(toIndentedString(hotline)).append("\n");
    sb.append("    address: ").append(toIndentedString(address)).append("\n");
    sb.append("    socialMedia: ").append(toIndentedString(socialMedia)).append("\n");
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
