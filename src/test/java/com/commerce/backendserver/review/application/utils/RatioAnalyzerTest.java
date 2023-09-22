package com.commerce.backendserver.review.application.utils;

import static com.commerce.backendserver.review.utils.ReviewAsserter.*;
import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.commerce.backendserver.common.fixture.ReviewFixture;
import com.commerce.backendserver.review.application.dto.response.RatioStatistic;
import com.commerce.backendserver.review.domain.additionalinfo.AdditionalInfo;
import com.commerce.backendserver.review.domain.additionalinfo.constants.InfoName;

/**
 * ReviewFixture 의 데이터가 바뀌면 테스트가 실패할 수 있음!! 주의!!
 */
@DisplayName("[RatioAnalyzer Test] - Application layer")
class RatioAnalyzerTest {

	private final RatioAnalyzer ratioAnalyzer = new RatioAnalyzer();

	@Test
	@DisplayName("[analyzeAdditionalInfo]")
	void analyzeAdditionalInfoTest() {
		//given
		List<List<AdditionalInfo>> additionalInfoData = generateAdditionalInfoData();

		//when
		Map<String, Map<String, RatioStatistic>> result = ratioAnalyzer.analyzeAdditionalInfo(additionalInfoData);

		//then
		assertThat(result).hasSize(3);

		assertAdditionalInfoStatistic(
			result,
			List.of("키", "몸무게", "사이즈"),
			List.of(
				Set.of(new RatioTestDto("156", 1), new RatioTestDto("172", 2)),
				Set.of(new RatioTestDto("60", 1), new RatioTestDto("40", 1)),
				Set.of(
					new RatioTestDto("Small", 1),
					new RatioTestDto("Medium", 1),
					new RatioTestDto("Large", 1)
				)
			)
		);
	}

	@Test
	@DisplayName("[analyzeScore]")
	void analyzeScoreTest() {
		//given
		List<Integer> scores = List.of(1, 2, 3, 3, 3, 4);

		//when
		Map<String, RatioStatistic> result = ratioAnalyzer.analyzeScore(scores);

		//then
		assertThat(result).hasSize(4);

		assertScoreStatistic(
			result,
			Set.of(
				new RatioTestDto("1", 1),
				new RatioTestDto("2", 1),
				new RatioTestDto("3", 3),
				new RatioTestDto("4", 1)
			)
		);
	}

	private List<List<AdditionalInfo>> generateAdditionalInfoData() {
		List<List<AdditionalInfo>> additionalInfoData = new ArrayList<>();

		Arrays.stream(ReviewFixture.values())
			.map(ReviewFixture::getStringInfoSet)
			.forEach(infoSet -> {
				List<AdditionalInfo> additionalInfoList = infoSet.stream()
					.map(stringInfo -> {
						String[] splitInfo = stringInfo.split("/");
						return AdditionalInfo.of(InfoName.matchInfoName(splitInfo[0]), splitInfo[1]);
					}).toList();

				additionalInfoData.add(additionalInfoList);
			});

		return additionalInfoData;
	}
}

