package com.amz.microservices.product.server.exception;

import com.amz.microservices.common.object.error.ErrorCode;
import com.amz.microservices.common.object.exception.BaseException;

public class ProductException extends BaseException {

  public ProductException(ErrorCode errorCode) {
    super(errorCode);
  }

}
