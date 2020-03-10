package com.amz.microservices.auth.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("AuthRequestDto")
public class AuthRequestDto {

  @NotBlank(message = "Username is required")
  @ApiModelProperty(value = "The username", required = true)
  private String username;

  @NotBlank(message = "Password is required")
  @ApiModelProperty(value = "The password", required = true)
  private String password;

}
