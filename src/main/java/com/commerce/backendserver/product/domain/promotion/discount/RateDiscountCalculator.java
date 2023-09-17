package com.commerce.backendserver.product.domain.promotion.discount;

import com.commerce.backendserver.global.exception.CommerceException;
import com.commerce.backendserver.product.domain.ProductPriceAttribute;

import static com.commerce.backendserver.product.exception.ProductError.INVALID_PROMOTION;

public class RateDiscountCalculator implements PromotionPriceCalculator {

    @Override
    public Integer getPromotionDiscountedAmount(
            ProductPriceAttribute attribute
    ) {
        validatePromotion(attribute);
        Integer discountAmount = attribute.getPromotion().getDiscountAttribute().getDiscountAmount();
        return attribute.getOriginPrice() / 100 * discountAmount;
    }

    private void validatePromotion(
            ProductPriceAttribute attribute
    ) {
        if (attribute.getPromotion().getDiscountAttribute().getDiscountAmount() < 0 || attribute.getPromotion().getDiscountAttribute().getDiscountAmount() >= 100) {
            throw CommerceException.of(INVALID_PROMOTION);
        }
    }
}
