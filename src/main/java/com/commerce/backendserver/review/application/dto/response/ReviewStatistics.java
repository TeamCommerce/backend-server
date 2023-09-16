package com.commerce.backendserver.review.application.dto.response;

import java.util.List;
import java.util.Map;
import java.util.Set;

public record ReviewStatistics(
	int totalReviewers,
	Set<String> existSizes,
	Map<Integer, RatioStatistic> scoreStatistics,
	double averageScore,
	List<AdditionalInfoStatistic> additionalInfoStatistics
) {
}
