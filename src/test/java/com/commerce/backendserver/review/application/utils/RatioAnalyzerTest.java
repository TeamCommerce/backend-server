package com.commerce.backendserver.review.application.utils;

import static com.commerce.backendserver.review.fixture.ReviewStatisticFixture.*;
import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
		Map<String, Map<String, RatioStatistic>> actual = ratioAnalyzer.analyzeAdditionalInfo(additionalInfoData);

		//then
		Map<String, Map<String, RatioStatistic>> expected = getExpectedAdditionalInfoStatistic();

		assertThat(actual.keySet()).hasSameSizeAs(expected.keySet());
		actual.forEach((key, value) -> assertThat(expected).containsEntry(key, value));
	}

	@Test
	@DisplayName("[analyzeScore]")
	void analyzeScoreTest() {
		//given
		List<Integer> scores = Arrays.stream(ReviewFixture.values())
			.mapToInt(ReviewFixture::getScore)
			.boxed().toList();

		//when
		Map<String, RatioStatistic> actual = ratioAnalyzer.analyzeScore(scores);

		//then
		Map<String, RatioStatistic> expected = getExpectedScoreStatistic();

		assertThat(actual.keySet()).hasSameSizeAs(expected.keySet());
		actual.forEach((key, value) -> assertThat(expected).containsEntry(key, value));
	}

	private List<List<AdditionalInfo>> generateAdditionalInfoData() {
		List<List<AdditionalInfo>> additionalInfoData = new ArrayList<>();

		Arrays.stream(ReviewFixture.values())
			.map(ReviewFixture::getStringInfoSet)
			.forEach(infoSet -> {
				List<AdditionalInfo> additionalInfoList = infoSet.stream()
					.map(stringInfo -> {
						String[] splitInfo = stringInfo.split("/");
						return AdditionalInfo.of(InfoName.getInfoName(splitInfo[0]), splitInfo[1]);
					}).toList();

				additionalInfoData.add(additionalInfoList);
			});

		return additionalInfoData;
	}
}

