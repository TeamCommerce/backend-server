package com.commerce.backendserver.review.application.utils.validator;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.commerce.backendserver.common.base.MockTestBase;

@DisplayName("[ContentsLengthValidator Test] (Application layer)")
class ContentsLengthValidatorTest extends MockTestBase {

	@Mock
	private ValidContentsLength validContentsLength;

	private final ContentsLengthValidator validator = new ContentsLengthValidator();

	@BeforeEach
	void setUp() {
		given(validContentsLength.min()).willReturn(5);

		validator.initialize(validContentsLength);
	}

	@Nested
	@DisplayName("[isValid method]")
	class isValidTest {

		@Test
		@DisplayName("success")
		void success() {
			//given
			final String contents = "hello";

			//when
			boolean result = validator.isValid(contents, null);

			//then
			assertThat(result).isTrue();
		}

		@Test
		@DisplayName("failByInvalidLength")
		void failByInvalidLength() {
			//given
			final String invalidContents = "t e s t";

			//when
			boolean result = validator.isValid(invalidContents, null);

			//then
			assertThat(result).isFalse();
		}
	}
}
