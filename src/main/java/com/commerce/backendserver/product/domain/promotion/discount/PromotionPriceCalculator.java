package com.commerce.backendserver.product.domain.promotion.discount;

import com.commerce.backendserver.product.domain.promotion.PromotionDiscountAttribute;

public interface PromotionPriceCalculator {

    Integer getPromotionDiscountedAmount(PromotionDiscountAttribute priceAttribute);
}
