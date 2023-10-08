package com.commerce.backendserver.product.domain;

import static com.commerce.backendserver.product.exception.ProductError.*;
import static com.commerce.backendserver.product.fixture.PromotionFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.commerce.backendserver.global.exception.CommerceException;
import com.commerce.backendserver.product.domain.promotion.Promotion;

@DisplayName("[ProductPriceAttribute Test] - Domain Layer")
class ProductPriceAttributeTest {

	@Nested
	@DisplayName("[of]")
	class ofTest {

		@Test
		@DisplayName("[Success]")
		void success() {
			//given
			final Promotion promotion = VALID_FIX_PROMOTION.toEntity();
			final Integer originPrice = 30000;

			//when
			ProductPriceAttribute result = ProductPriceAttribute.of(
				promotion,
				originPrice
			);

			//then
			assertAll(
				() -> assertThat(result.getPromotion()).isEqualTo(promotion),
				() -> assertThat(result.getOriginPrice()).isEqualTo(originPrice)
			);
		}

		@Test
		@DisplayName("[Fail] 상품 가격(originPrice)이 음의 정수일 때 실패")
		void FailWhenIsOriginPriceMinus() {
			//given & when & then
			assertThatThrownBy(
				() ->
					ProductPriceAttribute.of(VALID_FIX_PROMOTION.toEntity(), -3000))
				.isInstanceOf(CommerceException.class)
				.hasMessageContaining(INVALID_PRICE_ATTRIBUTE.getMessage());
		}
	}

	@Nested
	@DisplayName("[applyPromotionDiscount]")
	class applyPromotionDiscountTest {
		//todo
	}
}
