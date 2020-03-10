package com.amz.microservices.auth.server.exception;

import com.amz.microservices.common.object.error.ErrorCode;
import com.amz.microservices.common.object.exception.BaseException;

public class AuthException extends BaseException {

  public AuthException(ErrorCode errorCode) {
    super(errorCode);
  }

}
