package com.commerce.backendserver.product.domain.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductStatus {

    AVAILABLE("활성화 상품, 검색 가능, 구매 가능, 재고 positve"),
    UNAVAILABLE("활성화 상품, 검색 가능, 구매 불가, 재고 0"),
    FORBIDDEN("비활성화 상품, 검색 불가");

    private final String status;
}
