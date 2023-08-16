package com.commerce.backendserver.auth.exception;

import com.commerce.backendserver.global.exception.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthError implements ErrorCode {
    TOKEN_EXPIRED("만료된 토큰입니다.", HttpStatus.BAD_REQUEST),
    TOKEN_INVALID("유효하지 않은 토큰입니다.", HttpStatus.BAD_REQUEST),
    ;

    private final String message;
    private final HttpStatus status;
}
