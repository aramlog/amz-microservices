package com.amz.microservices.common.object.dto;

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
@ApiModel("ErrorFieldDto")
public class ErrorFieldDto {

  @ApiModelProperty(value = "Error field")
  private String field;

  @ApiModelProperty(value = "Error field default message")
  private String defaultMessage;

}
