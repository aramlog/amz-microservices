package com.amz.microservices.auth.server.error;

import com.amz.microservices.common.object.error.ErrorCode;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthErrorCode implements ErrorCode {

  AUTH_BAD_CREDENTIALS(HttpStatus.UNAUTHORIZED, 200),
  AUTH_REFRESH_TOKEN_INVALID(HttpStatus.UNAUTHORIZED, 201),
  AUTH_REFRESH_TOKEN_BLOCKED(HttpStatus.UNAUTHORIZED, 202),
  AUTH_REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, 203),
  AUTH_CREDENTIALS_ALREADY_EXISTS(HttpStatus.CONFLICT, 204),
  AUTH_CREDENTIALS_USERNAME_ALREADY_EXISTS(HttpStatus.CONFLICT, 205),
  AUTH_CREDENTIALS_NOT_FOUND(HttpStatus.NOT_FOUND, 206);

  private HttpStatus httpStatus;
  private int errorCode;

  @Override
  @JsonValue
  public String getErrorSlag() {
    return name();
  }

}
