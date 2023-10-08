package com.commerce.backendserver.review.domain;

import static com.commerce.backendserver.review.exception.ReviewError.*;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.ThrowableAssert.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.commerce.backendserver.global.exception.CommerceException;

@DisplayName("[Score Test] - Domain layer")
class ScoreTest {

	@Nested
	@DisplayName("[new]")
	class newTest {

		@Test
		@DisplayName("[Success] 점수가 범위 내에 있어 생성에 성공한다")
		void success() {
			//given
			final int score = 3;

			//when
			Score result = new Score(score);

			//then
			assertThat(result.getValue()).isEqualTo(score);
		}

		@Test
		@DisplayName("[Fail] 점수가 범위 밖에 있어 생성에 실패한다")
		void failWhenScoreOutOfRange() {
			//given
			final int score = 9;

			//when
			ThrowingCallable when = () -> new Score(score);

			//then
			assertThatThrownBy(when)
				.isInstanceOf(CommerceException.class)
				.hasMessageContaining(INVALID_RANGE_SCORE.getMessage());
		}
	}
}
