package com.commerce.backendserver.review.fixture;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.commerce.backendserver.review.application.dto.response.RatioStatistic;
import com.commerce.backendserver.review.application.dto.response.ReviewStatistics;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 해당 Fixture 은 ReviewFixture 의 데이터를 기반으로 제작됨
 * ReviewFixture 가 변경되면 해당 클래스의 데이터도 변경되야함
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ReviewStatisticFixture {

	public static Map<String, Map<String, RatioStatistic>> getExpectedAdditionalInfoStatistic() {
		Map<String, Map<String, RatioStatistic>> statistic = new HashMap<>();
		statistic.put("키", Map.of(
			"156", new RatioStatistic(1, 33.33333333333333),
			"172", new RatioStatistic(2, 66.66666666666666)
		));
		statistic.put("몸무게", Map.of(
			"60", new RatioStatistic(1, 50.0),
			"40", new RatioStatistic(1, 50.0)
		));
		statistic.put("사이즈", Map.of(
			"작음", new RatioStatistic(1, 33.33333333333333),
			"딱_맞음", new RatioStatistic(1, 33.33333333333333),
			"생각보다_큼", new RatioStatistic(1, 33.33333333333333)
		));

		return statistic;
	}

	public static Map<String, RatioStatistic> getExpectedScoreStatistic() {
		Map<String, RatioStatistic> statistic = new HashMap<>();
		statistic.put("2", new RatioStatistic(1, 33.33333333333333));
		statistic.put("3", new RatioStatistic(1, 33.33333333333333));
		statistic.put("4", new RatioStatistic(1, 33.33333333333333));

		return statistic;
	}

	public static ReviewStatistics getExpectedReviewStatistics() {
		Set<String> existSizes = new HashSet<>(List.of("작음", "딱_맞음", "생각보다_큼"));

		return new ReviewStatistics(
			3,
			3.0,
			existSizes,
			getExpectedScoreStatistic(),
			getExpectedAdditionalInfoStatistic()
		);
	}
}
