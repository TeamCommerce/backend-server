package com.commerce.backendserver.review.application.dto.response;

import java.util.Map;
import java.util.Set;

public record ReviewStatistics(
	int totalReviewCount,
	double averageScore,
	Set<String> existSizes,
	Map<String, RatioStatistic> scoreStatistics,
	Map<String, Map<String, RatioStatistic>> additionalInfoStatistics
) {
}
