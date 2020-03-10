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
@ApiModel("RefreshTokenDto")
public class RefreshTokenDto {

  @ApiModelProperty(value = "Refreshed access token")
  private String accessToken;

}
