package com.commerce.backendserver.product.domain.promotion.constants;

import com.commerce.backendserver.product.domain.promotion.discount.FixDiscountCalculator;
import com.commerce.backendserver.product.domain.promotion.discount.PromotionPriceCalculator;
import com.commerce.backendserver.product.domain.promotion.discount.RateDiscountCalculator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PromotionType {
	FIX_DISCOUNT(new FixDiscountCalculator()),
	RATE_DISCOUNT(new RateDiscountCalculator()),
	NO_PROMOTION(priceAttribute -> 0);

	private final PromotionPriceCalculator calculator;
}
