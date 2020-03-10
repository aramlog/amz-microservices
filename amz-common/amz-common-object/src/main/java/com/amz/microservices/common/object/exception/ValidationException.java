package com.amz.microservices.common.object.exception;

import com.amz.microservices.common.object.dto.ErrorFieldDto;
import com.amz.microservices.common.object.error.ErrorCode;
import java.util.List;

public class ValidationException extends BaseException {

  public ValidationException(ErrorCode errorCode) {
    super(errorCode);
  }

  public ValidationException(ErrorCode errorCode, Object[] errorParams) {
    super(errorCode, errorParams);
  }

  public ValidationException(ErrorCode errorCode, String debugMessage) {
    super(errorCode, debugMessage);
  }

  public ValidationException(ErrorCode errorCode, List<ErrorFieldDto> errors) {
    super(errorCode, errors);
  }

  public ValidationException(ErrorCode errorCode, String debugMessage, List<ErrorFieldDto> errors) {
    super(errorCode, debugMessage, errors, null);
  }
}
