package com.amz.microservices.account.dto.account;

import com.amz.microservices.common.object.enums.GenderType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value = "AccountUpdateDto")
public class AccountUpdateDto {

  @ApiModelProperty(value = "Username", example = "anna123", required = true)
  private String username;

  @Size(max = 128, message = "Allowable max length is 128")
  @ApiModelProperty(value = "First name", example = "Anna")
  private String firstName;

  @Size(max = 128, message = "Allowable max length is 128")
  @ApiModelProperty(value = "Last name", example = "Peterson")
  private String lastName;

  @Email(message = "Email is not valid")
  @Size(max = 256, message = "Allowable max length is 256")
  @ApiModelProperty(value = "Email address", example = "anna.petertson@example.com")
  private String email;

  @Size(max = 50, message = "Allowable max length is 50")
  @ApiModelProperty(value = "Mobile number", example = "+1 (202) 555-0172")
  private String mobile;

  @ApiModelProperty(value = "Gender", example = "FEMALE", allowableValues = "MALE, FEMALE, OTHER")
  private GenderType gender;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = com.amz.microservices.common.object.Pattern.DATE_PATTERN)
  @ApiModelProperty(value = "Date of birth", example = "1988-01-01")
  private Date birthday;
}


