package com.commerce.backendserver.image.exception;

import com.commerce.backendserver.global.exception.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
@RequiredArgsConstructor
public enum ImageError implements ErrorCode {

    // [Domain] Image
    INVALID_IMAGE_URL("이미지 URL이 올바르지 않습니다", BAD_REQUEST),
    INVALID_IMAGE_PATH("이미지 경로가 올바르지 않습니다", BAD_REQUEST);

    private final String message;
    private final HttpStatus status;
}
