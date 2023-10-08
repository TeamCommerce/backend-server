package com.commerce.backendserver.review.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.commerce.backendserver.review.application.dto.request.ReviewAnalyticsCondition;
import com.commerce.backendserver.review.application.dto.response.ReviewStatistics;
import com.commerce.backendserver.review.application.utils.ReviewAnalyzer;
import com.commerce.backendserver.review.domain.Review;
import com.commerce.backendserver.review.infra.persistence.ReviewQueryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewAnalyticsService {

	private final ReviewQueryRepository reviewQueryRepository;
	private final ReviewAnalyzer reviewAnalyzer;

	public ReviewStatistics getReviewStatistics(ReviewAnalyticsCondition condition) {
		List<Review> reviews = reviewQueryRepository.findReviewByStatisticCondition(
			condition.engColorNames(),
			condition.sizes(),
			condition.additionalOptions(),
			condition.scores(),
			condition.additionalInfoList()
		);

		return reviewAnalyzer.analyzeReview(reviews);
	}
}
