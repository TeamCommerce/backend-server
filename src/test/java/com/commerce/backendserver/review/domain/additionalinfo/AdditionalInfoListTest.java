package com.commerce.backendserver.review.domain.additionalinfo;

import static com.commerce.backendserver.common.fixture.ReviewFixture.*;
import static com.commerce.backendserver.review.exception.ReviewError.*;
import static java.util.Comparator.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.commerce.backendserver.global.exception.CommerceException;
import com.commerce.backendserver.review.domain.additionalinfo.constants.InfoName;

@DisplayName("[AdditionalInfoList Test] (Domain layer)")
class AdditionalInfoListTest {

	private List<AdditionalInfo> generateExpectedAdditionalInfo(final Set<String> stringInfoSet) {

		return stringInfoSet.stream()
			.map(info -> {
				String[] split = info.split("/");
				return AdditionalInfo.of(InfoName.valueOf(split[0]), split[1]);
			})
			.sorted(comparingInt(info -> info.getInfoName().getOrder()))
			.toList();
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

		@Test
		@DisplayName("[Fail] 잘못된 추가정보 형식으로 실패한다")
		void failByInvalidAdditionalInfo() {
			//given
			Set<String> infoSet = Set.of("SIZE|Large");

			//when, then
			assertThatThrownBy(() -> AdditionalInfoList.of(infoSet, null))
				.isInstanceOf(CommerceException.class)
				.hasMessageContaining(INVALID_ADDITIONAL_INFO.getMessage());
		}

		@Test
		@DisplayName("[Fail] 정수값을 입력해야하는 추가정보에 정수를 입력하지 않아 실패한다")
		void failWhenPresentInvalidIntegerValue() {
			//given
			Set<String> infoSet = Set.of("HEIGHT/150cm");

			//when, then
			assertThatThrownBy(() -> AdditionalInfoList.of(infoSet, null))
				.isInstanceOf(CommerceException.class)
				.hasMessageContaining(INVALID_INTEGER_INFO_VALUE.getMessage());
		}
	}
}
