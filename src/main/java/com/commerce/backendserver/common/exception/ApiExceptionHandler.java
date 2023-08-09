package com.commerce.backendserver.common.exception;

import com.commerce.backendserver.common.exception.error.ErrorCode;
import com.commerce.backendserver.common.exception.error.ErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    log.error("", ex);
    return ErrorResponse.of(HttpStatus.BAD_REQUEST, ex.getFieldError());
  }

  @ExceptionHandler(CommerceException.class)
  public ResponseEntity<ErrorResponse> handleLearnchaException(CommerceException ex) throws JsonProcessingException {
    log.error("", ex);
    return convert(ex.getErrorCode());
  }

  private ResponseEntity<ErrorResponse> convert(ErrorCode errorCode) {
    return ResponseEntity.status(errorCode.getStatus()).body(ErrorResponse.of(errorCode));
  }
}
