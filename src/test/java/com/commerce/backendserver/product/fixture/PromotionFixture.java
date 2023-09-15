package com.commerce.backendserver.product.fixture;

import com.commerce.backendserver.product.domain.promotion.Promotion;
import com.commerce.backendserver.product.domain.promotion.PromotionDiscountAttribute;
import com.commerce.backendserver.product.domain.promotion.constants.PromotionType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.commerce.backendserver.product.domain.promotion.constants.PromotionType.FIX_DISCOUNT;

@Getter
@RequiredArgsConstructor
public enum PromotionFixture {
    VALID_PROMOTION(
            "유효한 프로모션",
            FIX_DISCOUNT,
            3000),

    TOO_HIGH_PROMOTION(
            "INT_MIN 할인 프로모션",
            FIX_DISCOUNT,
            2147483647),

    MINUS_FIX_PROMOTION(
            "-30000원 할인 프로모션",
            FIX_DISCOUNT,
            -30000);

    private final String name;
    private final PromotionType type;
    private final Integer discountAmount;

    public Promotion toEntity() {
        return Promotion.of(
                name,
                PromotionDiscountAttribute.of(type, discountAmount));
    }
}
