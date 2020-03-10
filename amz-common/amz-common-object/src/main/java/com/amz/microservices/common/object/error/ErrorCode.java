package com.amz.microservices.common.object.error;

import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.http.HttpStatus;

public interface ErrorCode {

  @JsonValue
  String getErrorSlag();

  HttpStatus getHttpStatus();

  int getErrorCode();

}
