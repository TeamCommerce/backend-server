package com.commerce.backendserver.image.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import com.commerce.backendserver.product.fixture.ProductFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.commerce.backendserver.common.fixture.ReviewFixture;
import com.commerce.backendserver.product.domain.Product;
import com.commerce.backendserver.review.domain.Review;

@DisplayName("[ReviewImage Test] (Domain layer)")
class ReviewImageTest {

	@Test
	@DisplayName("[Construct method]")
	void ofTest() {
		//given
		Product product = ProductFixture.VALID_PRODUCT.toEntity(null);
		Review review = ReviewFixture.A.toEntity(product, 1L);

		final String url = "testUrl.jpg";

		//when
		ReviewImage result = ReviewImage.of(url, review);

		//then
		assertAll(
			() -> assertThat(result.getReview()).isEqualTo(review),
			() -> assertThat(result.getUrl()).isEqualTo(url)
		);
	}
}
