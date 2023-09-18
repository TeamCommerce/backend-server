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

    // [Domain] Product
    INVALID_PRICE_ATTRIBUTE("상품 가격 정보가 올바르지 않습니다.", BAD_REQUEST),
    INVALID_OPTIONS("상품 옵션 정보가 올바르지 않습니다.", BAD_REQUEST),
    PRODUCT_NOT_FOUND("상품 정보를 찾을 수 없습니다.", NOT_FOUND),
    INVALID_PRODUCT_MAIN_IMAGE("상품 메인 사진 정보를 찾을 수 없습니다.", NOT_FOUND),

    // [Domain] ProductCommonInfo
    UNKNOWN_BRAND("존재하지 않는 브랜드입니다.", BAD_REQUEST),
    INVALID_BRAND("브랜드 정보가 올바르지 않습니다.", BAD_REQUEST),
    INVALID_PRODUCT_NAME("상품 이름이 올바르지 않습니다.", BAD_REQUEST),
    INVALID_PRODUCT_CATEGORY("상품 분류가 올바르지 않습니다.", BAD_REQUEST),
    INVALID_PRODUCT_DESCRIPTION("상품 설명 정보가 올바르지 않습니다.", BAD_REQUEST),
    TOO_LONG_PRODUCT_DESCRIPTION("상품 설명 정보가 제약조건 이상의 길이입니다.", BAD_REQUEST),

    // [Domain] ProductPriceAttribute
    INVALID_PROMOTION("상품 프로모션 정보가 올바르지 않습니다.", BAD_REQUEST),
    INVALID_ORIGIN_PRICE("상품 정가가 올바르지 않습니다.", BAD_REQUEST),
    MINUS_APPLIED_PROMOTION_PRICE("상품 프로모션 할인 적용가는 음수일 수 없습니다.", BAD_REQUEST);


    private final String message;
    private final HttpStatus status;
}
