package com.commerce.backendserver.review.stub;

import java.util.List;

import com.commerce.backendserver.review.application.dto.response.ReviewStatistics;
import com.commerce.backendserver.review.application.utils.ReviewAnalyzer;
import com.commerce.backendserver.review.domain.Review;
import com.commerce.backendserver.review.fixture.ReviewStatisticFixture;

public class ReviewAnalyzerStub extends ReviewAnalyzer {

	public ReviewAnalyzerStub() {
		super(null);
	}

	@Override
	public ReviewStatistics analyzeReview(final List<Review> reviews) {
		return ReviewStatisticFixture.getExpectedReviewStatistics();
	}
}
