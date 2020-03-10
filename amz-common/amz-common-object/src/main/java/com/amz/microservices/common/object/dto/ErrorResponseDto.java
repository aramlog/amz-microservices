package com.amz.microservices.common.object.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel("ErrorResponseDto")
public class ErrorResponseDto {

  @ApiModelProperty(value = "Error slag", required = true)
  private String errorSlag;

  @ApiModelProperty(value = "System error code", required = true)
  private int errorCode;

  @ApiModelProperty(value = "Error message", required = true)
  private String errorMessage;

  @ApiModelProperty(value = "Debug message")
  private String debugMessage;

  @ApiModelProperty(value = "HTTP Status", required = true)
  private int statusCode;

  @ApiModelProperty(value = "List of errors")
  private List<ErrorFieldDto> errors;

}
