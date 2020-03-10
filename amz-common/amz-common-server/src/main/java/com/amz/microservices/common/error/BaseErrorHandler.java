package com.amz.microservices.common.error;

import com.amz.microservices.common.object.dto.ErrorResponseDto;
import com.amz.microservices.common.object.exception.BaseException;

public interface BaseErrorHandler {

  default ErrorResponseDto buildErrorResponseDto(BaseException ex) {
    return ErrorResponseDto.builder()
        .errorSlag(ex.getErrorSlag())
        .errorCode(ex.getErrorCode())
        .debugMessage(ex.getDebugMessage())
        .statusCode(ex.getStatusCode())
        .errors(ex.getErrors())
        .build();
  }

}
