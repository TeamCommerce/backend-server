package com.commerce.backendserver.review.application.dto.response;

import java.util.Map;

public record AdditionalInfoStatistic(
	Map<String, RatioStatistic> statistics
) {
}
