package com.commerce.backendserver.global.exception.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
@RequiredArgsConstructor
public enum GlobalError implements ErrorCode {
    GLOBAL_NOT_FOUND("Not Found Error!", NOT_FOUND);

    private final String message;

    private final HttpStatus status;
}
