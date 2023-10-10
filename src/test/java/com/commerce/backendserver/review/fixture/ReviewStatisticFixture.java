package com.commerce.backendserver.review.fixture;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.commerce.backendserver.review.application.dto.response.RatioStatistic;
import com.commerce.backendserver.review.utils.ReviewAsserter.RatioTestDto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 해당 Fixture 은 ReviewFixture 의 데이터를 기반으로 제작됨
 * ReviewFixture 가 변경되면 해당 클래스의 데이터도 변경되야함
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ReviewStatisticFixture {

	public static final List<String> ADDITIONAL_INFO_KEY_DATA =
		List.of("키", "몸무게", "사이즈");

	public static final List<String> EXIST_SIZE_DATA =
		List.of("Small", "Large", "Medium");

	public static final Set<RatioTestDto> HEIGHT_DATA =
		Set.of(new RatioTestDto("156", 1), new RatioTestDto("172", 2));

	public static final Set<RatioTestDto> WEIGHT_DATA =
		Set.of(new RatioTestDto("60", 1), new RatioTestDto("40", 1));

	public static final Set<RatioTestDto> SIZE_DATA =
		Set.of(
			new RatioTestDto("Small", 1),
			new RatioTestDto("Medium", 1),
			new RatioTestDto("Large", 1)
		);

	public static final Set<RatioTestDto> SCORE_DATA =
		Set.of(
			new RatioTestDto("2", 1),
			new RatioTestDto("3", 1),
			new RatioTestDto("4", 1)
		);

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
			"Small", new RatioStatistic(1, 33.33333333333333),
			"Medium", new RatioStatistic(1, 33.33333333333333),
			"Large", new RatioStatistic(1, 33.33333333333333)
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
}
