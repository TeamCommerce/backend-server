package com.commerce.backendserver.review.utils;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

import com.commerce.backendserver.review.application.dto.response.RatioStatistic;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class ReviewAsserter {

	public static void assertScoreStatistic(
		Map<String, RatioStatistic> result,
		Set<RatioTestDto> dtoSet
	) {
		int totalCount = dtoSet.stream().mapToInt(dto -> dto.count).sum();

		dtoSet.forEach(dto -> {
			RatioStatistic statistic = result.get(dto.key);
			assertThat(statistic).isNotNull();

			assertAll(
				() -> assertThat(statistic.reviewers()).isEqualTo(dto.count),
				() -> assertThat(statistic.ratio()).isEqualTo((double)dto.count / totalCount * 100)
			);
		});
	}

	private static void assertSingleAdditionalInfoStatistic(
		final Map<String, Map<String, RatioStatistic>> result,
		final String key,
		final Set<RatioTestDto> dtoSet
	) {
		int totalCount = dtoSet.stream().mapToInt(dto -> dto.count).sum();

		Map<String, RatioStatistic> valueResult = result.get(key);
		assertThat(valueResult).isNotNull();

		dtoSet.forEach(dto -> {
			RatioStatistic statistic = valueResult.get(dto.key);
			assertThat(statistic).isNotNull();

			assertAll(
				() -> assertThat(statistic.reviewers()).isEqualTo(dto.count),
				() -> assertThat(statistic.ratio()).isEqualTo((double)dto.count / totalCount * 100)
			);
		});
	}

	/**
	 * @param result : 실제 결과값
	 * @param keyList : 기댓값 AdditionalInfoStatistic 의 key list
	 * @param dtoSetList : 기댓값 AdditionalInfoStatistic 의 value 인 Map 의 key, key 각각의 count(개수) 를 담은 list
	 */
	public static void assertAdditionalInfoStatistic(
		final Map<String, Map<String, RatioStatistic>> result,
		final List<String> keyList,
		final List<Set<RatioTestDto>> dtoSetList
	) {
		assertThat(keyList).hasSameSizeAs(dtoSetList);

		IntStream.range(0, keyList.size())
			.forEach(i -> {
				String key = keyList.get(i);
				Set<RatioTestDto> dtoSet = dtoSetList.get(i);

				assertSingleAdditionalInfoStatistic(
					result,
					key,
					dtoSet
				);
			});
	}

	public record RatioTestDto(
		String key,
		int count
	) {
	}
}
