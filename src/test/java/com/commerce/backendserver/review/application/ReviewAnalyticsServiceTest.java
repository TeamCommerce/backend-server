package com.commerce.backendserver.review.application;

import static com.commerce.backendserver.review.utils.ReviewAsserter.*;
import static org.mockito.BDDMockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.commerce.backendserver.common.base.MockTestBase;
import com.commerce.backendserver.common.fixture.ReviewFixture;
import com.commerce.backendserver.review.application.dto.request.ReviewAnalyticsCondition;
import com.commerce.backendserver.review.application.dto.response.ReviewStatistics;
import com.commerce.backendserver.review.application.utils.RatioAnalyzer;
import com.commerce.backendserver.review.application.utils.ReviewAnalyzer;
import com.commerce.backendserver.review.domain.Review;
import com.commerce.backendserver.review.infra.persistence.ReviewQueryRepository;

@DisplayName("[ReviewAnalyticsService Test] - Application layer")
class ReviewAnalyticsServiceTest extends MockTestBase {

	private ReviewAnalyticsService reviewAnalyticsService;

	@Mock
	private ReviewQueryRepository reviewQueryRepository;

	@BeforeEach
	void setUp() {
		reviewAnalyticsService = new ReviewAnalyticsService(
			reviewQueryRepository,
			new ReviewAnalyzer(new RatioAnalyzer())
		);
	}

	@Test
	@DisplayName("[getReviewStatistics]")
	void getReviewStatisticsTest() {
		//given
		List<Review> reviews = Arrays.stream(ReviewFixture.values())
			.map(fixture -> fixture.toEntity(null, null, null))
			.toList();

		given(reviewQueryRepository.findReviewByStatisticCondition(
			null, null, null, null, null
		)).willReturn(reviews);

		ReviewAnalyticsCondition condition = new ReviewAnalyticsCondition(
			null, null, null, null, null
		);

		//when
		ReviewStatistics result = reviewAnalyticsService.getReviewStatistics(condition);

		//then
		assertReviewStatistic(result, reviews);
	}
}
