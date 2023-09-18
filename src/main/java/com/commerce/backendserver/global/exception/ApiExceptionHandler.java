package com.commerce.backendserver.global.exception;

import java.util.Optional;

import com.commerce.backendserver.global.exception.error.ErrorCode;
import com.commerce.backendserver.global.exception.error.ErrorResponse;
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
        return ErrorResponse.of(Optional.ofNullable(ex.getFieldError()));
    }

    @ExceptionHandler(CommerceException.class)
    public ResponseEntity<ErrorResponse> handleCommerceException(CommerceException ex) {
        logCommerceException(ex);
        return convert(ex.getErrorCode());
    }

    private ResponseEntity<ErrorResponse> convert(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getStatus()).body(ErrorResponse.of(errorCode));
    }

    private void logCommerceException(CommerceException ex) {
        if (ex.getErrorCode().getStatus().is5xxServerError()) {
            log.error("", ex);
        } else log.error("error message = {}", ex.getMessage());
    }
}
