package com.commerce.backendserver.global.exception.error;

import static com.commerce.backendserver.global.exception.error.GlobalError.*;

import lombok.Getter;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.Optional;

@Getter
public class ErrorResponse {

    private final String timeStamp;

    private final String errorCode;

    private final String errorMessage;

    private ErrorResponse(String errorCode, String errorMessage) {
        this.timeStamp = LocalDateTime.now().toString();
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public static ErrorResponse of(ErrorCode errorCode) {
        return new ErrorResponse(errorCode.getCode(), errorCode.getMessage());
    }

    public static ErrorResponse of(Optional<FieldError> fieldError) {
        return fieldError.map(error -> new ErrorResponse(error.getCode(), error.getDefaultMessage()))
            .orElseGet(() -> ErrorResponse.of(INVALID_REQUEST_PARAM));
    }
}
