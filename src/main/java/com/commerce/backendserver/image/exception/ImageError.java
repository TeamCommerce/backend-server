package com.commerce.backendserver.image.exception;

import com.commerce.backendserver.global.exception.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Getter
@RequiredArgsConstructor
public enum ImageError implements ErrorCode {
    INVALID_IMAGE_TYPE("이미지 업로드에 실패했습니다.(type)", INTERNAL_SERVER_ERROR, "I_001"),
    EMPTY_FILE("빈 파일입니다.", BAD_REQUEST, "I_002"),
    UPLOAD_FAIL("이미지 업로드에 실패했습니다.", INTERNAL_SERVER_ERROR, "I_003"),
    ;

    private final String message;
    private final HttpStatus status;
    private final String code;
}
