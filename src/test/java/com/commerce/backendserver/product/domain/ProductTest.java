package com.commerce.backendserver.product.domain;

import static com.commerce.backendserver.product.fixture.ProductFixture.*;
import static com.commerce.backendserver.product.fixture.PromotionFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.commerce.backendserver.product.domain.promotion.Promotion;
import com.commerce.backendserver.product.fixture.ProductFixture;

@DisplayName("[Product Test] - Domain Layer")
class ProductTest {

	@Nested
	@DisplayName("[createProduct]")
	class ofTest {

		private final Promotion promotion = VALID_FIX_PROMOTION.toEntity();

		@Test
		@DisplayName("[Success]")
		void success() {
			//given
			final ProductFixture fixture = VALID_PRODUCT;

			//when
			final Product product = fixture.toEntity(promotion);

			//then
			assertAll(
				// ProductCommonInfo
				() -> assertThat(fixture.getBrand()).isEqualTo(product.getCommonInfo().getBrand()),
				() -> assertThat(fixture.getName()).isEqualTo(product.getCommonInfo().getName()),
				() -> assertThat(fixture.getCategory()).isEqualTo(product.getCommonInfo().getCategory()),
				() -> assertThat(fixture.getDescription()).isEqualTo(product.getCommonInfo().getDescription()),
				// ProductPriceInfo
				() -> assertThat(fixture.getOriginPrice()).isEqualTo(product.getPriceAttribute().getOriginPrice()),
				() -> assertThat(promotion).isEqualTo(product.getPriceAttribute().getPromotion()),
				// ProductImages
				() -> assertThat(fixture.getImages().size()).isEqualTo(product.getImages().size())
			);
		}
	}
}
