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
@ApiModel("RefreshTokenRequestDto")
public class RefreshTokenRequestDto {

  @NotBlank(message = "Refresh token is required")
  @ApiModelProperty(value = "The refresh token", required = true)
  private String refreshToken;

}
