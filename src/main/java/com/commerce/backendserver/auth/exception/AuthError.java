package com.commerce.backendserver.auth.exception;

import com.commerce.backendserver.global.exception.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum AuthError implements ErrorCode {
    TOKEN_EXPIRED("만료된 토큰입니다.", BAD_REQUEST, "A_001"),
    TOKEN_INVALID("유효하지 않은 토큰입니다.", BAD_REQUEST, "A_002"),
    NOT_EXIST_OAUTH_TYPE("지원하지 않는 소셜 로그인 타입 입니다.", BAD_REQUEST, "A_003"),
    NEED_LOGIN("로그인이 필요합니다.", UNAUTHORIZED, "A_004"),
    INVALID_AUTHORIZATION_HEADER("올바르지 않은 인증 헤더입니다", BAD_REQUEST, "A_005"),

    GOOGLE_OAUTH_ERROR("구글 서버 통신간 오류입니다.", INTERNAL_SERVER_ERROR, "A_006"),
    KAKAO_OAUTH_ERROR("카카오 서버 통신간 오류입니다.", INTERNAL_SERVER_ERROR, "A_007"),
    ;

    private final String message;
    private final HttpStatus status;
    private final String code;
}
