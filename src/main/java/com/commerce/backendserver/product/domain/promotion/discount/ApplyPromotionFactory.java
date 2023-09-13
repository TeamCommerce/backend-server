package com.commerce.backendserver.product.domain.promotion.discount;

import com.commerce.backendserver.global.exception.CommerceException;
import com.commerce.backendserver.product.domain.promotion.constants.PromotionType;
import org.springframework.stereotype.Service;

import static com.commerce.backendserver.product.exception.ProductError.INVALID_PROMOTION;

@Service
public class ApplyPromotionFactory {

    public PromotionPriceCalculator calculateDiscountedAmount(
            PromotionType type
    ) {
        return switch (type) {
            case FIX_DISCOUNT -> new FixDiscountCalculator();
            case RATE_DISCOUNT -> new RateDiscountCalculator();
            default -> throw CommerceException.of(INVALID_PROMOTION);
        };
    }
}
