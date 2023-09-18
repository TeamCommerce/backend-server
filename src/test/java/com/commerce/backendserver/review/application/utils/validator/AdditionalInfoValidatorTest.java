package com.commerce.backendserver.review.application.utils.validator;

import com.commerce.backendserver.global.exception.CommerceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.commerce.backendserver.common.fixture.ReviewFixture.A;
import static com.commerce.backendserver.review.exception.ReviewError.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("[AdditionalInfoValidator Test] - Application layer")
class AdditionalInfoValidatorTest {

	private final AdditionalInfoValidator validator = new AdditionalInfoValidator();

	@Nested
	@DisplayName("[isValid]")
	class isValid {

		@Test
		@DisplayName("[Success] 정상 인자일 때 성공")
		void success() {
			//given
			Set<String> stringInfoSet = A.getStringInfoSet();

			//when
			boolean result = validator.isValid(stringInfoSet, null);

			//then
			assertThat(result).isTrue();
		}

		@Test
		@DisplayName("[Success] Value가 Null일 때 성공")
		void successWhenValueIsNull() {
			//when
			boolean result = validator.isValid(null, null);

			//then
			assertThat(result).isTrue();
		}

		@Test
		@DisplayName("[Fail] 잘못된 포맷으로 인해 실패")
		void failWhenPresentInvalidFormat() {
			//given
			Set<String> invalidValue = Set.of("SIZE|Large");

			//when, then
			assertThatThrownBy(() -> validator.isValid(invalidValue, null))
					.isInstanceOf(CommerceException.class)
					.hasMessageContaining(INVALID_ADDITIONAL_INFO.getMessage());
		}

		@Test
		@DisplayName("[Fail] 잘못된 정수 값 입력시 실패")
		void failWhenPresentInvalidIntegerValue() {
			//given
			Set<String> invalidValue = Set.of("HEIGHT/150cm");

			//when, then
			assertThatThrownBy(() -> validator.isValid(invalidValue, null))
					.isInstanceOf(CommerceException.class)
					.hasMessageContaining(INVALID_INTEGER_INFO_VALUE.getMessage());
		}

		@Test
		@DisplayName("[Fail] 존재하지 않는 이름 입력시 실패")
		void failWhenPresentNotExistInfoName() {
			//given
			Set<String> invalidValue = Set.of("HELLO/Large");

			//when, then
			assertThatThrownBy(() -> validator.isValid(invalidValue, null))
					.isInstanceOf(CommerceException.class)
					.hasMessageContaining(NOT_EXIST_INFO_NAME.getMessage());
		}
	}
}
