package com.amz.microservices.common.object.exception;

import com.amz.microservices.common.object.dto.ErrorFieldDto;
import com.amz.microservices.common.object.error.ErrorCode;
import java.util.List;
import lombok.Getter;

@Getter
public abstract class BaseException extends RuntimeException {

  private final String errorSlag;
  private final int errorCode;
  private final int statusCode;
  private final String debugMessage;
  private final Object[] errorParams;
  private final List<ErrorFieldDto> errors;

  public BaseException(ErrorCode errorCode) {
    this(errorCode, null, null, null);
  }

  public BaseException(ErrorCode errorCode, Object[] errorParams) {
    this(errorCode, null, null, errorParams);
  }

  public BaseException(ErrorCode errorCode, String debugMessage) {
    this(errorCode, debugMessage, null, null);
  }

  public BaseException(ErrorCode errorCode, List<ErrorFieldDto> errors) {
    this(errorCode, null, errors, null);
  }

  public BaseException(ErrorCode errorCode, String debugMessage, List<ErrorFieldDto> errors, Object[] errorParams) {
    super(debugMessage);
    this.errorSlag = errorCode.getErrorSlag();
    this.errorCode = errorCode.getErrorCode();
    this.statusCode = errorCode.getHttpStatus().value();
    this.debugMessage = debugMessage;
    this.errorParams = errorParams;
    this.errors = errors;
  }

}
