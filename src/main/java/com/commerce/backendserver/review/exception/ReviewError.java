package com.commerce.backendserver.review.exception;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import com.commerce.backendserver.global.exception.error.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReviewError implements ErrorCode {

	INVALID_LENGTH("최소 5자 이상으로 작성해주세요.", BAD_REQUEST, "R_001"),
	NOT_EXIST_INFO_NAME("존재하지 않는 추가 정보 이름입니다.", BAD_REQUEST, "R_002"),
	INVALID_INTEGER_INFO_VALUE("숫자 관련 추가 정보에는 숫자만 입력해주세요.", BAD_REQUEST, "R_003"),
	INVALID_ADDITIONAL_INFO("잘못된 형식의 추가정보 입니다.", BAD_REQUEST, "R_004"),
	NOT_MATCH_PRODUCT_OPTION_ID("상품 옵션이 상품에 존재하지 않습니다.", BAD_REQUEST, "R_005"),
	INVALID_RANGE_SCORE("1에서 5사이의 정수 별점을 입력해주세요.", BAD_REQUEST, "R_006");

	private final String message;
	private final HttpStatus status;
	private final String code;
}
