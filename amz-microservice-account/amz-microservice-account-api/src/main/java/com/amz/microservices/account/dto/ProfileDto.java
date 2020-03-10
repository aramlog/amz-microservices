package com.amz.microservices.account.dto;

import com.amz.microservices.common.object.Pattern;
import com.amz.microservices.common.object.enums.GenderType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.validation.constraints.Email;
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
@JsonPropertyOrder
@ApiModel("ProfileDto")
public class ProfileDto {

  @NotBlank(message = "Required")
  @Size(max = 128, message = "Allowable max length is 128")
  @ApiModelProperty(value = "Account first name", example = "John")
  private String firstName;

  @NotBlank(message = "Required")
  @Size(max = 128, message = "Allowable max length is 128")
  @ApiModelProperty(value = "Account last name", example = "Doe")
  private String lastName;

  @NotBlank(message = "Required")
  @Email(message = "Email address is invalid")
  @Size(max = 256, message = "Allowable max length is 256")
  @ApiModelProperty(value = "Account email address", example = "john@example.com")
  private String email;

  @Size(max = 50, message = "Allowable max length is 50")
  @ApiModelProperty(value = "Account mobile", example = "+1 (202) 555-0172")
  private String mobile;

  @ApiModelProperty(value = "Gender", example = "FEMALE", allowableValues = "MALE, FEMALE, OTHER")
  private GenderType gender;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Pattern.DATE_PATTERN)
  @ApiModelProperty(value = "Date of birth", example = "1994-05-07")
  private Date birthday;
}
