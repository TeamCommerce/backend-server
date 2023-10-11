package com.commerce.backendserver.review.application.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.commerce.backendserver.review.application.dto.response.RatioStatistic;
import com.commerce.backendserver.review.application.dto.response.ReviewStatistics;
import com.commerce.backendserver.review.domain.Review;
import com.commerce.backendserver.review.domain.additionalinfo.AdditionalInfo;
import com.commerce.backendserver.review.domain.additionalinfo.constants.InfoName;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReviewAnalyzer {

	private static final String SIZE = InfoName.SIZE.getValue();

	private final RatioAnalyzer ratioAnalyzer;

	public ReviewStatistics analyzeReview(final List<Review> reviews) {
		List<Integer> scoreData = toScoreData(reviews);

		Map<String, RatioStatistic> scoreStatistic = ratioAnalyzer.analyzeScore(scoreData);

		Map<String, Map<String, RatioStatistic>> additionalInfoStatistic = ratioAnalyzer
			.analyzeAdditionalInfo(toAdditionalInfoData(reviews));

		int totalReviewCount = reviews.size();

		Set<String> existSizes = additionalInfoStatistic.getOrDefault(SIZE, new HashMap<>()).keySet();

		return new ReviewStatistics(
			totalReviewCount,
			calculateAverage(totalReviewCount, calculateTotalScore(scoreData)),
			existSizes,
			scoreStatistic,
			additionalInfoStatistic
		);
	}

	private int calculateTotalScore(List<Integer> scoreData) {
		return scoreData.stream().mapToInt(i -> i).sum();
	}

	private List<List<AdditionalInfo>> toAdditionalInfoData(final List<Review> reviews) {
		return reviews.stream()
			.map(Review::getAdditionalInfoList)
			.toList();
	}

	private List<Integer> toScoreData(final List<Review> reviews) {
		return reviews.stream()
			.map(Review::getScore)
			.toList();
	}

	private double calculateAverage(final double size, final double sum) {
		if (sum == 0) {
			return 0;
		}
		return sum / size;
	}
}
