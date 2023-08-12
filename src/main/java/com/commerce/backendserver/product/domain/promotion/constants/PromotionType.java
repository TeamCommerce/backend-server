package com.commerce.backendserver.product.domain.promotion.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PromotionType {
    FIX_DISCOUNT("정액 할인"),
    RATE_DISCOUNT("정률 할인");

    private final String type;
}
