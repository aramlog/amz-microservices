package com.amz.microservices.account.server.error;

import com.amz.microservices.common.object.error.ErrorCode;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AccountErrorCode implements ErrorCode {

  ACCOUNT_NOT_FOUND(HttpStatus.NOT_FOUND, 300),
  ACCOUNT_USERNAME_ALREADY_EXISTS(HttpStatus.CONFLICT, 301),
  ACCOUNT_EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, 302),
  ACCOUNT_MOBILE_ALREADY_EXISTS(HttpStatus.CONFLICT, 303),
  ACCOUNT_ALREADY_DELETED(HttpStatus.PRECONDITION_FAILED, 304);

  private HttpStatus httpStatus;
  private int errorCode;

  @Override
  @JsonValue
  public String getErrorSlag() {
    return name();
  }

}
