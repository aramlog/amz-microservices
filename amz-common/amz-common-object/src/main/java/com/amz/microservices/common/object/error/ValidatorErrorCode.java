package com.amz.microservices.common.object.error;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ValidatorErrorCode implements ErrorCode {

  MISSING_REQUIRED_FIELDS(HttpStatus.BAD_REQUEST, 100),
  ACCESS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, 101),
  ACCESS_TOKEN_INVALID(HttpStatus.UNAUTHORIZED, 102);

  private HttpStatus httpStatus;
  private int errorCode;

  @Override
  @JsonValue
  public String getErrorSlag() {
    return name();
  }

}
