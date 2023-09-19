package com.commerce.backendserver.image.domain;

import com.commerce.backendserver.common.fixture.ReviewFixture;
import com.commerce.backendserver.product.domain.Product;
import com.commerce.backendserver.product.fixture.ProductFixture;
import com.commerce.backendserver.review.domain.Review;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("[ReviewImage Test] - Domain layer")
class ReviewImageTest {

	@Test
	@DisplayName("[Construct method]")
	void ofTest() {
		//given
		Product product = ProductFixture.VALID_PRODUCT.toEntity(null);
		Review review = ReviewFixture.A.toEntity(product, 1L, 1L);

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
