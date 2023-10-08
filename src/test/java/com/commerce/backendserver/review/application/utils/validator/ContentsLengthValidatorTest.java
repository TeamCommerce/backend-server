package com.commerce.backendserver.review.application.utils.validator;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.commerce.backendserver.common.base.MockTestBase;

@DisplayName("[ContentsLengthValidator Test] - Application layer")
class ContentsLengthValidatorTest extends MockTestBase {

	private final ContentsLengthValidator validator = new ContentsLengthValidator();
	@Mock
	private ValidContentsLength validContentsLength;

	@BeforeEach
	void setUp() {
		given(validContentsLength.min()).willReturn(5);

		validator.initialize(validContentsLength);
	}

	@Nested
	@DisplayName("[isValid]")
	class isValidTest {

		@Test
		@DisplayName("[success]")
		void success() {
			//given
			final String contents = "hello";

			//when
			boolean result = validator.isValid(contents, null);

			//then
			assertThat(result).isTrue();
		}

		@Test
		@DisplayName("[Fail] 유효하지 않은 길이로 실패")
		void failWhenPresentInvalidLength() {
			//given
			final String invalidContents = "t e s t";

			//when
			boolean result = validator.isValid(invalidContents, null);

			//then
			assertThat(result).isFalse();
		}
	}
}
