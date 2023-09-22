package com.commerce.backendserver.review.utils;

import static com.commerce.backendserver.review.fixture.ReviewStatisticFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

import com.commerce.backendserver.review.application.dto.response.RatioStatistic;
import com.commerce.backendserver.review.application.dto.response.ReviewStatistics;
import com.commerce.backendserver.review.domain.Review;

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
	 * @param actual : 실제 결과값
	 * @param keyList : 기댓값 AdditionalInfoStatistic 의 key list
	 * @param dtoSetList : 기댓값 AdditionalInfoStatistic 의 value 인 Map 의 key, key 각각의 count(개수) 를 담은 list
	 */
	public static void assertAdditionalInfoStatistic(
		final Map<String, Map<String, RatioStatistic>> actual,
		final List<String> keyList,
		final List<Set<RatioTestDto>> dtoSetList
	) {
		assertThat(keyList).hasSameSizeAs(dtoSetList);

		IntStream.range(0, keyList.size())
			.forEach(i -> {
				String key = keyList.get(i);
				Set<RatioTestDto> dtoSet = dtoSetList.get(i);

				assertSingleAdditionalInfoStatistic(
					actual,
					key,
					dtoSet
				);
			});
	}

	/**
	 * @param actual : 실제 결과값
	 * @param reviews : 기댓값 제작을 위한 review
	 */
	public static void assertReviewStatistic(
		final ReviewStatistics actual,
		final List<Review> reviews
	) {
		double expectedAverage = generateAverage(reviews);

		assertAll(
			() -> assertThat(actual.totalReviewCount()).isEqualTo(reviews.size()),
			() -> assertThat(actual.averageScore()).isEqualTo(expectedAverage),
			() -> assertThat(actual.existSizes()).containsAll(EXIST_SIZE_DATA),
			() -> assertScoreStatistic(
				actual.scoreStatistics(),
				SCORE_DATA
			),
			() -> assertAdditionalInfoStatistic(
				actual.additionalInfoStatistics(),
				ADDITIONAL_INFO_KEY_DATA,
				List.of(
					HEIGHT_DATA,
					WEIGHT_DATA,
					SIZE_DATA
				)
			)
		);
	}

	private static double generateAverage(List<Review> reviews) {
		double totalScore = reviews.stream().mapToDouble(Review::getScore).sum();
		return totalScore / (double)reviews.size();
	}

	public record RatioTestDto(
		String key,
		int count
	) {
	}
}
