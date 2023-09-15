package com.commerce.backendserver.review.domain.likereview;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("[LikeReview Test] (Domain layer)")
class LikeReviewTest {

	@Test
	@DisplayName("[of method]")
	void ofTest() {
		//given
		final Long memberId = 1L;
		final Long reviewId = 1L;

		//when
		LikeReview result = LikeReview.of(memberId, reviewId);

		//then
		assertAll(
			() -> assertThat(result.getMemberId()).isEqualTo(memberId),
			() -> assertThat(result.getReviewId()).isEqualTo(reviewId)
		);
	}
}