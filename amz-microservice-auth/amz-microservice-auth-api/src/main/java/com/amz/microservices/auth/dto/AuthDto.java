package com.amz.microservices.auth.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("AuthDto")
public class AuthDto {

  @ApiModelProperty(value = "JWT access token for accessing APIs protected endpoints")
  private String accessToken;

  @ApiModelProperty(value = "The refresh token for renewing access token")
  private String refreshToken;

  @ApiModelProperty(value = "Authorization header prefix before access token", example = "Bearer")
  private String tokenType;

  @ApiModelProperty(value = "Access token expiration time in seconds", example = "3600")
  private Long expiresIn;

}
