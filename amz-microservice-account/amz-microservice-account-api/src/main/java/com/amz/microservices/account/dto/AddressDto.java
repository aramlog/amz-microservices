package com.amz.microservices.account.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.UUID;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel("AddressDto")
public class AddressDto {

  @ApiModelProperty(value = "Address unique identifier", example = "c3281e9b-dec2-46db-beba-a9968e268836")
  private UUID id;

  @NotBlank(message = "Country is required")
  @Size(max = 128, message = "Allowable max length is 128")
  @ApiModelProperty(value = "Country", example = "Hartford", required = true)
  private String country;

  @NotBlank(message = "City is required")
  @Size(max = 128, message = "Allowable max length is 128")
  @ApiModelProperty(value = "City", example = "Los Angeles", required = true)
  private String city;

  @NotBlank(message = "Address line 1 is required")
  @Size(max = 128, message = "Allowable max length is 128")
  @ApiModelProperty(value = "Address line 1", example = "St. Mark's Place", required = true)
  private String addressLine1;

  @Size(max = 128, message = "Allowable max length is 128")
  @ApiModelProperty(value = "Address line 2", example = "BD6712")
  private String addressLine2;

  @NotBlank(message = "State is required")
  @Size(max = 128, message = "Allowable max length is 128")
  @ApiModelProperty(value = "State", example = "California", required = true)
  private String state;

  @NotBlank(message = "Zip is required")
  @Size(max = 16, message = "Allowable max length is 16")
  @ApiModelProperty(value = "Zip code", example = "99334", required = true)
  private String zipCode;
}
