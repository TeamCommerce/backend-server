package com.commerce.backendserver.image.exception;

import com.commerce.backendserver.global.exception.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ImageError implements ErrorCode {

    ;

    private final String message;
    private final HttpStatus status;
}
