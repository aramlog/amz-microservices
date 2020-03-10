package com.amz.microservices.common.error;

import static com.amz.microservices.common.object.error.ValidatorErrorCode.MISSING_REQUIRED_FIELDS;

import com.amz.microservices.common.object.dto.ErrorFieldDto;
import com.amz.microservices.common.object.dto.ErrorResponseDto;
import com.amz.microservices.common.object.exception.BaseException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ErrorHandler implements BaseErrorHandler {

  @ExceptionHandler(BaseException.class)
  public ResponseEntity<ErrorResponseDto> handleBaseException(BaseException ex) {
    log.error("Received service exception. Error {}", ex.getErrorSlag());

    final ErrorResponseDto errorResponse = buildErrorResponseDto(ex);

    return ResponseEntity.status(errorResponse.getStatusCode()).body(errorResponse);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    log.error("Received method argument not valid exception. Error {}", ex.getMessage());

    // Collect validation error for response
    final List<ErrorFieldDto> errorParams = ex.getBindingResult().getAllErrors()
        .stream()
        .map(error -> {
          final String fieldName = ((FieldError) error).getField();
          final String errorMessage = error.getDefaultMessage();

          return new ErrorFieldDto(fieldName, errorMessage);
        }).collect(Collectors.toList());

    final ErrorResponseDto errorResponse = ErrorResponseDto.builder()
        .errorSlag(MISSING_REQUIRED_FIELDS.getErrorSlag())
        .errorCode(MISSING_REQUIRED_FIELDS.getErrorCode())
        .debugMessage(ex.getMessage())
        .statusCode(MISSING_REQUIRED_FIELDS.getHttpStatus().value())
        .errors(errorParams)
        .build();

    return ResponseEntity.status(MISSING_REQUIRED_FIELDS.getHttpStatus()).body(errorResponse);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorResponseDto> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
    log.error("Received bad request exception: {}", ex.getLocalizedMessage());

    final ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
        .errorSlag(HttpStatus.BAD_REQUEST.name())
        .debugMessage(ex.getLocalizedMessage())
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .build();

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDto);
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ErrorResponseDto> handleAccessDeniedException(AccessDeniedException ex) {
    log.error("Received access denied exception: {}", ex.getLocalizedMessage());

    final ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
        .errorSlag(HttpStatus.FORBIDDEN.name())
        .debugMessage(ex.getLocalizedMessage())
        .statusCode(HttpStatus.FORBIDDEN.value())
        .build();

    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponseDto);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponseDto> handleException(Exception ex) {
    log.error("Received exception: {}", ex.getMessage());

    final ErrorResponseDto errorResponse = ErrorResponseDto.builder()
        .errorSlag(HttpStatus.INTERNAL_SERVER_ERROR.name())
        .debugMessage(ex.getLocalizedMessage())
        .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .build();

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
  }

}
