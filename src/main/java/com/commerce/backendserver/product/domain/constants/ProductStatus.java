package com.commerce.backendserver.product.domain.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductStatus {

    AVAILABLE_FOR_ORDER("주문 가능"),
    OUT_OF_STOCK("재고 부족"),
    DISABLED("비활성화 상품");

    private final String status;
}
