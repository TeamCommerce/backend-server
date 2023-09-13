package com.commerce.backendserver.product.domain.promotion.discount;

import com.commerce.backendserver.global.exception.CommerceException;
import com.commerce.backendserver.product.domain.promotion.PromotionDiscountAttribute;

import static com.commerce.backendserver.product.exception.ProductError.INVALID_PROMOTION;

public class RateDiscountCalculator implements PromotionPriceCalculator {

    @Override
    public Integer getPromotionDiscountedAmount(
            PromotionDiscountAttribute priceAttribute
    ) {
        if (isInvalidPromotion(priceAttribute)) {
            throw CommerceException.of(INVALID_PROMOTION);
        }
        Integer discountAmount = priceAttribute.getDiscountAmount();
        return discountAmount / 100 * discountAmount;
    }

    @Override
    public boolean isInvalidPromotion(
            PromotionDiscountAttribute priceAttribute
    ) {
        Integer discountAmount = priceAttribute.getDiscountAmount();
        return discountAmount <= 0 || discountAmount > 100;
    }
}
