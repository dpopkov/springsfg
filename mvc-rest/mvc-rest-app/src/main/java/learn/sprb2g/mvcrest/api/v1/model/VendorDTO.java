package learn.sprb2g.mvcrest.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VendorDTO {
    private Long id;
    @ApiModelProperty(value = "Name of the Vendor", required = true)
    private String name;

    @ApiModelProperty("URL of the Vendor")
    @JsonProperty("vendor_url")
    private String vendorUrl;
}
