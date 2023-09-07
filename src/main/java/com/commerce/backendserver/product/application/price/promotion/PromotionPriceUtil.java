package com.commerce.backendserver.product.application.price.promotion;

import com.commerce.backendserver.product.domain.promotion.Promotion;

public interface PromotionPriceUtil {

    Integer getAppliedPromotionAmount(Integer originPrice, Promotion promotion);

    boolean isInvalidPromotion(Promotion promotion);
}
