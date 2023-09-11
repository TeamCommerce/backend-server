package com.commerce.backendserver.product.domain.promotion.discount;

import com.commerce.backendserver.global.exception.CommerceException;
import com.commerce.backendserver.product.domain.promotion.PromotionDiscountAttribute;

import static com.commerce.backendserver.product.exception.ProductError.INVALID_PROMOTION;

public class FixDiscountCalculator implements PromotionPriceCalculator {

    @Override
    public Integer getPromotionDiscountedAmount(
            PromotionDiscountAttribute priceAttribute
    ) {
        if (isInvalidPromotion(priceAttribute)) {
            throw CommerceException.of(INVALID_PROMOTION);
        }
        return priceAttribute.getDiscountAmount();
    }

    @Override
    public boolean isInvalidPromotion(
            PromotionDiscountAttribute priceAttribute
    ) {
        return priceAttribute.getDiscountAmount() <= 0;
    }
}
