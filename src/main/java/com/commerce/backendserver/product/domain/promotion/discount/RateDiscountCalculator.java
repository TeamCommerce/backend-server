package com.commerce.backendserver.product.domain.promotion.discount;

import static com.commerce.backendserver.product.exception.ProductError.*;

import com.commerce.backendserver.global.exception.CommerceException;
import com.commerce.backendserver.product.domain.ProductPriceAttribute;

public class RateDiscountCalculator implements PromotionPriceCalculator {

	@Override
	public Integer getPromotionDiscountedAmount(
		ProductPriceAttribute attribute
	) {
		validatePromotion(attribute);
		Integer promotionValue = attribute.getPromotion().getPromotionValue();
		return attribute.getOriginPrice() / 100 * promotionValue;
	}

	private void validatePromotion(
		ProductPriceAttribute attribute
	) {
		if (attribute.getPromotion().getPromotionValue() < 0 || attribute.getPromotion().getPromotionValue() >= 100) {
			throw CommerceException.of(INVALID_PROMOTION);
		}
	}
}
