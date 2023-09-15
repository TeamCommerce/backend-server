package com.commerce.backendserver.global.exception.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
public class ErrorResponse {

    private final String timeStamp;

    private final int errorCode;

    private final String errorMessage;

    private ErrorResponse(int errorCode, String errorMessage) {
        this.timeStamp = LocalDateTime.now().toString();
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public static ErrorResponse of(ErrorCode errorCode) {
        return new ErrorResponse(errorCode.getStatus().value(), errorCode.getMessage());
    }

    public static ErrorResponse of(HttpStatus httpStatus, Optional<FieldError> fieldError) {
        return fieldError.map(error -> new ErrorResponse(httpStatus.value(), error.getDefaultMessage()))
            .orElseGet(() -> new ErrorResponse(httpStatus.value(), "Invalid Param"));
    }
}
