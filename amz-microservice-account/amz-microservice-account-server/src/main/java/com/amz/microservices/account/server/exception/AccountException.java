package com.amz.microservices.account.server.exception;

import com.amz.microservices.common.object.error.ErrorCode;
import com.amz.microservices.common.object.exception.BaseException;

public class AccountException extends BaseException {

  public AccountException(ErrorCode errorCode) {
    super(errorCode);
  }

}
