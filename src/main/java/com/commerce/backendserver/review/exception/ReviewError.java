package com.commerce.backendserver.review.exception;

import com.commerce.backendserver.global.exception.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
@RequiredArgsConstructor
public enum ReviewError implements ErrorCode {

    INVALID_LENGTH("최소 5자 이상으로 작성해주세요.", BAD_REQUEST),
    NOT_EXIST_INFO_NAME("존재하지 않는 추가 정보 이름입니다.", BAD_REQUEST),
    INVALID_INTEGER_INFO_VALUE("숫자 관련 추가 정보에는 숫자만 입력해주세요.", BAD_REQUEST),
    ;

    private final String message;
    private final HttpStatus status;
}
