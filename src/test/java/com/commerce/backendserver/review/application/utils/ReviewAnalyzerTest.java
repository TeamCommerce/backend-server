package com.commerce.backendserver.review.application.utils;

import static com.commerce.backendserver.review.utils.ReviewAsserter.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.commerce.backendserver.common.fixture.ReviewFixture;
import com.commerce.backendserver.review.application.dto.response.ReviewStatistics;
import com.commerce.backendserver.review.domain.Review;

@DisplayName("[DefaultReviewAnalyzer Test] - Application layer")
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
		double expectedAverage = generateAverage(reviews);

		assertAll(
			() -> assertThat(result.totalReviewCount()).isEqualTo(reviews.size()),
			() -> assertThat(result.averageScore()).isEqualTo(expectedAverage),
			() -> assertThat(result.existSizes()).containsAll(List.of("Small", "Large", "Medium")),
			() -> assertAdditionalInfoStatistic(
				result.additionalInfoStatistics(),
				List.of("키", "몸무게", "사이즈"),
				List.of(
					Set.of(new RatioTestDto("156", 1), new RatioTestDto("172", 2)),
					Set.of(new RatioTestDto("60", 1), new RatioTestDto("40", 1)),
					Set.of(
						new RatioTestDto("Small", 1),
						new RatioTestDto("Medium", 1),
						new RatioTestDto("Large", 1)
					)
				)
			)
		);
	}

	private double generateAverage(List<Review> reviews) {
		double totalScore = reviews.stream().mapToDouble(Review::getScore).sum();
		return totalScore / (double)reviews.size();
	}
}
