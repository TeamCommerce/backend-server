package com.commerce.backendserver.review.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

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
	}
}
