package com.commerce.backendserver.review.application.utils;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.commerce.backendserver.common.fixture.ReviewFixture;
import com.commerce.backendserver.review.application.dto.response.ReviewStatistics;
import com.commerce.backendserver.review.domain.Review;
import com.commerce.backendserver.review.fixture.ReviewStatisticFixture;

@DisplayName("[ReviewAnalyzer Test] - Application layer")
class ReviewAnalyzerTest {

	private final ReviewAnalyzer reviewAnalyzer = new ReviewAnalyzer(new RatioAnalyzer());

	@Test
	@DisplayName("[analyzeReview]")
	void analyzeReviewTest() {
		//given
		List<Review> reviews = Arrays.stream(ReviewFixture.values())
			.map(fixture -> fixture.toEntity(null, null, null))
			.toList();

		//when
		ReviewStatistics result = reviewAnalyzer.analyzeReview(reviews);

		//then
		ReviewStatistics expected = ReviewStatisticFixture.getExpectedReviewStatistics();

		assertThat(result).isEqualTo(expected);
	}
}
