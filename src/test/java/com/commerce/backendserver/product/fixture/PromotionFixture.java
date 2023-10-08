package com.commerce.backendserver.product.fixture;

import static com.commerce.backendserver.product.domain.promotion.constants.PromotionType.*;

import com.commerce.backendserver.product.domain.promotion.Promotion;
import com.commerce.backendserver.product.domain.promotion.constants.PromotionType;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PromotionFixture {
	VALID_FIX_PROMOTION(
		"유효한 3000원 정액 프로모션",
		FIX_DISCOUNT,
		3000),

	VALID_RATE_PROMOTION(
		"유효한 30% 정률 프로모션",
		RATE_DISCOUNT,
		30),

	TOO_HIGH_PROMOTION(
		"INT_MIN 할인 프로모션",
		FIX_DISCOUNT,
		2147483647),

	MINUS_FIX_PROMOTION(
		"-30000원 할인 프로모션",
		FIX_DISCOUNT,
		-30000);

	private final String name;
	private final PromotionType type;
	private final Integer promotionValue;

	public Promotion toEntity() {
		return Promotion.of(name, type, promotionValue);
	}
}
