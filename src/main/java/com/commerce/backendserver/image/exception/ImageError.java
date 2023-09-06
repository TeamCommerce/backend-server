package com.commerce.backendserver.image.exception;

import static org.springframework.http.HttpStatus.*;

import com.commerce.backendserver.global.exception.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ImageError implements ErrorCode {
    INVALID_IMAGE_TYPE("이미지 업로드에 실패했습니다.(type)", INTERNAL_SERVER_ERROR),
    EMPTY_FILE("빈 파일입니다.", BAD_REQUEST),
    UPLOAD_FAIL("이미지 업로드에 실패했습니다.", INTERNAL_SERVER_ERROR),
    ;

    private final String message;
    private final HttpStatus status;
}
