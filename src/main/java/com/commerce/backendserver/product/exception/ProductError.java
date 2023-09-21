package com.commerce.backendserver.product.exception;

import com.commerce.backendserver.global.exception.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
@RequiredArgsConstructor
public enum ProductError implements ErrorCode {

    INVALID_PRICE_ATTRIBUTE("상품 가격 정보가 올바르지 않습니다.", BAD_REQUEST, "P_001"),
    PRODUCT_NOT_FOUND("상품 정보를 찾을 수 없습니다.", NOT_FOUND, "P_002"),
    INVALID_PRODUCT_MAIN_IMAGE("상품 메인 사진 정보를 찾을 수 없습니다.", NOT_FOUND, "P_003"),
    INVALID_PRODUCT_DESCRIPTION("상품 설명 정보가 올바르지 않습니다.", BAD_REQUEST, "P_004"),
    TOO_LONG_PRODUCT_DESCRIPTION("상품 설명 정보가 제약조건 이상의 길이입니다.", BAD_REQUEST, "P_005"),
    INVALID_PROMOTION("상품 프로모션 정보가 올바르지 않습니다.", BAD_REQUEST, "P_006"),
    MINUS_APPLIED_PROMOTION_PRICE("상품 프로모션 할인 적용가는 음수일 수 없습니다.", BAD_REQUEST, "P_007");


    private final String message;
    private final HttpStatus status;
    private final String code;
}
