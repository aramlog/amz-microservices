package com.amz.microservices.product.server.error;

import com.amz.microservices.common.object.error.ErrorCode;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ProductErrorCode implements ErrorCode {

  PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, 400),
  PRODUCT_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, 401);

  private HttpStatus httpStatus;
  private int errorCode;

  @Override
  @JsonValue
  public String getErrorSlag() {
    return name();
  }

}
