package com.commerce.backendserver.review.domain.additionalinfo;

import static com.commerce.backendserver.common.fixture.ReviewFixture.*;
import static java.util.Comparator.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.commerce.backendserver.review.domain.additionalinfo.constants.InfoName;

@DisplayName("[AdditionalInfoList Test] (Domain layer)")
class AdditionalInfoListTest {

	private List<AdditionalInfo> generateExpectedAdditionalInfo(Set<String> stringInfoSet) {
		List<AdditionalInfo> expected = new ArrayList<>(
			stringInfoSet.stream()
				.map(info -> {
					String[] split = info.split("/");
					return AdditionalInfo.of(InfoName.valueOf(split[0]), split[1]);
				}).toList()
		);

		expected.sort(comparingInt(info -> info.getInfoName().getOrder()));

		return expected;
	}

	private void assertAdditionalInfoMatching(
		List<AdditionalInfo> actual,
		List<AdditionalInfo> expected
	) {
		assertThat(actual).hasSameSizeAs(expected);

		IntStream.range(0, actual.size())
			.forEach(i -> {
				AdditionalInfo actualInfo = actual.get(i);
				AdditionalInfo expectedInfo = expected.get(i);
				assertAll(
					() -> assertThat(actualInfo.getInfoName()).isEqualTo(expectedInfo.getInfoName()),
					() -> assertThat(actualInfo.getInfoValue()).isEqualTo(expectedInfo.getInfoValue())
				);
			});
	}

	@Nested
	@DisplayName("[of]")
	class ofTest {

		@Test
		@DisplayName("[Success] StringInfoSet이 Null이 아닐 때 성공")
		void SuccessWhenPresentNotNullStringInfoSet() {
			//given
			Set<String> stringInfoSet = A.getStringInfoSet();

			//when
			AdditionalInfoList result = AdditionalInfoList.of(stringInfoSet, null);

			//then
			List<AdditionalInfo> expected = generateExpectedAdditionalInfo(stringInfoSet);

			assertAdditionalInfoMatching(result.getList(), expected);
		}

		@Test
		@DisplayName("[Success] StringInfoSet이 Null일 때 성공")
		void SuccessWhenPresentNullStringInfoSet() {
			//when
			AdditionalInfoList result = AdditionalInfoList.of(null, null);

			//then
			assertThat(result.getList()).isEmpty();
		}
	}
}
