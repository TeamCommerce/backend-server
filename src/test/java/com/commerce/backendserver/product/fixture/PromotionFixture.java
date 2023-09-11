package com.commerce.backendserver.product.fixture;

import com.commerce.backendserver.product.domain.promotion.Promotion;
import com.commerce.backendserver.product.domain.promotion.PromotionDiscountAttribute;
import com.commerce.backendserver.product.domain.promotion.PromotionPeriodAttribute;
import com.commerce.backendserver.product.domain.promotion.constants.PromotionType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@Getter
@RequiredArgsConstructor
public enum PromotionFixture {
    VALID_PROMOTION(
            "유효한 프로모션",
            PromotionType.FIX_DISCOUNT,
            3000);

    private final String name;
    private final PromotionType type;
    private final Integer discountAmount;

    public Promotion toEntity() {
        return Promotion.of(
                name,
                PromotionDiscountAttribute.of(type, discountAmount),
                PromotionPeriodAttribute.of(now().minusHours(3), now().plusHours(3)));
    }
}
