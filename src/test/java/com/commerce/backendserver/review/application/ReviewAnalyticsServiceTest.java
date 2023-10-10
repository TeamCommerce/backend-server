package com.commerce.backendserver.review.application;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.commerce.backendserver.common.base.MockTestBase;
import com.commerce.backendserver.review.application.dto.request.ReviewAnalyticsCondition;
import com.commerce.backendserver.review.application.dto.response.ReviewStatistics;
import com.commerce.backendserver.review.fixture.ReviewStatisticFixture;
import com.commerce.backendserver.review.stub.ReviewAnalyzerStub;
import com.commerce.backendserver.review.stub.ReviewRepositoryStub;

@DisplayName("[ReviewAnalyticsService Test] - Application layer")
class ReviewAnalyticsServiceTest extends MockTestBase {

	private final ReviewAnalyticsService reviewAnalyticsService;

	public ReviewAnalyticsServiceTest() {
		this.reviewAnalyticsService = new ReviewAnalyticsService(new ReviewRepositoryStub(), new ReviewAnalyzerStub());
	}

	@Test
	@DisplayName("[getReviewStatistics]")
	void getReviewStatisticsTest() {
		//given
		ReviewAnalyticsCondition condition = new ReviewAnalyticsCondition(
			null, null, null, null, null
		);

		//when
		ReviewStatistics actual = reviewAnalyticsService.getReviewStatistics(condition);

		//then
		ReviewStatistics expected = ReviewStatisticFixture.getExpectedReviewStatistics();
		assertThat(actual).isEqualTo(expected);
	}
}
