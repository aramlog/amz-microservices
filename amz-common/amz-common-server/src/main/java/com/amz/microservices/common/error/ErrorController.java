package com.amz.microservices.common.error;

import com.amz.microservices.common.object.dto.ErrorResponseDto;
import com.amz.microservices.common.object.exception.BaseException;
import javax.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.data.util.CastUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

@Slf4j
@RestController
public class ErrorController extends AbstractErrorController implements BaseErrorHandler {

  @Getter
  @Value("${error.path:/error}")
  private String errorPath;

  private final ErrorAttributes errorAttributes;

  private static final String UNKNOWN_ERROR = "UNKNOWN_ERROR";

  @Autowired
  public ErrorController(ErrorAttributes errorAttributes) {
    super(errorAttributes);
    this.errorAttributes = errorAttributes;
  }

  @RequestMapping(value = "${error.path:/error}", produces = "application/json")
  public ResponseEntity<ErrorResponseDto> error(HttpServletRequest request) {
    final Throwable t = errorAttributes.getError(new ServletWebRequest(request));

    if (t instanceof BaseException) {
      final BaseException ex = CastUtils.cast(t);

      final ErrorResponseDto errorResponse = buildErrorResponseDto(ex);

      return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
    }

    final ErrorResponseDto errorResponse = ErrorResponseDto.builder()
        .errorSlag(UNKNOWN_ERROR)
        .debugMessage(t.getMessage())
        .build();

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
  }

}
