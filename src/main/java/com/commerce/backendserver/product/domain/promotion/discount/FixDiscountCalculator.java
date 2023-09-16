package com.commerce.backendserver.product.domain.promotion.discount;

import com.commerce.backendserver.global.exception.CommerceException;
import com.commerce.backendserver.product.domain.promotion.PromotionDiscountAttribute;

import static com.commerce.backendserver.product.exception.ProductError.INVALID_PROMOTION;

public class FixDiscountCalculator implements PromotionPriceCalculator {

    @Override
    public Integer getPromotionDiscountedAmount(
            PromotionDiscountAttribute priceAttribute
    ) {
        validatePromotion(priceAttribute);
        return priceAttribute.getDiscountAmount();
    }

    private void validatePromotion(
            PromotionDiscountAttribute priceAttribute
    ) {
        if (priceAttribute.getDiscountAmount() < 0) {
            throw CommerceException.of(INVALID_PROMOTION);
        }
    }
}
