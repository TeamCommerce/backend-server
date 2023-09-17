package com.commerce.backendserver.product.domain.promotion.constants;

import com.commerce.backendserver.product.domain.promotion.discount.FixDiscountCalculator;
import com.commerce.backendserver.product.domain.promotion.discount.PromotionPriceCalculator;
import com.commerce.backendserver.product.domain.promotion.discount.RateDiscountCalculator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PromotionType {
    FIX_DISCOUNT("정액 할인", new FixDiscountCalculator()),
    RATE_DISCOUNT("정률 할인", new RateDiscountCalculator()),
    NO_PROMOTION("할인 없음",  priceAttribute -> 0);

    private final String type;
    private final PromotionPriceCalculator calculator;
}
