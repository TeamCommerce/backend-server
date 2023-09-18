package com.commerce.backendserver.review.application.utils.validator;

import com.commerce.backendserver.common.base.MockTestBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@DisplayName("[ScoreValidator Test] -Application layer")
class ScoreValidatorTest extends MockTestBase {

	@Mock
	private ValidScore validScore;

	private final ScoreValidator validator = new ScoreValidator();

	@BeforeEach
	void setUp() {
		//given
		given(validScore.min()).willReturn(1);
		given(validScore.max()).willReturn(5);

		validator.initialize(validScore);
	}

	@Nested
	@DisplayName("[isValid method]")
	class initializeTest {

		@Test
		@DisplayName("success")
		void success() {
			//when
			boolean result = validator.isValid(3, null);

			//then
			assertThat(result).isTrue();
		}

		@Test
		@DisplayName("fail by score out of bound")
		void failWhenScoreOutOfBound() {
			//when
			boolean result = validator.isValid(0, null);

			//then
			assertThat(result).isFalse();
		}
	}
}
