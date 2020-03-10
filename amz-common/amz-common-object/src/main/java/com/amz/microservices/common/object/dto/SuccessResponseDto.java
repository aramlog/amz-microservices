package com.amz.microservices.common.object.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel("SuccessResponseDto")
public class SuccessResponseDto<C> {

  @ApiModelProperty(value = "Success message", required = true)
  private String message;

  @ApiModelProperty(value = "Content dto")
  private C content;

}
