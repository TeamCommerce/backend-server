package com.commerce.backendserver.product.domain.promotion.discount;

import com.commerce.backendserver.product.domain.ProductPriceAttribute;

public interface PromotionPriceCalculator {

    Integer getPromotionDiscountedAmount(ProductPriceAttribute attribute);
}
