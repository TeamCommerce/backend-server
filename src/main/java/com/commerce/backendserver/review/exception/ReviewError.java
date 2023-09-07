package com.commerce.backendserver.review.exception;

import com.commerce.backendserver.global.exception.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
@RequiredArgsConstructor
public enum ReviewError implements ErrorCode {

    INVALID_LENGTH("최소 5자 이상으로 작성해주세요.", BAD_REQUEST);

    private final String message;
    private final HttpStatus status;
}
