package com.commerce.backendserver.review.domain;

import static com.commerce.backendserver.review.exception.ReviewError.*;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.ThrowableAssert.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.commerce.backendserver.global.exception.CommerceException;

@DisplayName("[Score Test] - Domain layer")
class ContentsTest {

	@Nested
	@DisplayName("[new]")
	class newTest {

		@Test
		@DisplayName("[Success] 콘텐츠 생성에 성공한다")
		void success() {
			//given
			final String contents = "hello";

			//when
			Contents result = new Contents(contents);

			//then
			assertThat(result.getValue()).isEqualTo(contents);
		}

		@Test
		@DisplayName("[Fail] 5자 이하의 콘텐츠 길이로 생성에 실패한다")
		void failByInvalidLength() {
			//given
			final String contents = "g         o";

			//when
			ThrowingCallable when = () -> new Contents(contents);

			//then
			assertThatThrownBy(when)
				.isInstanceOf(CommerceException.class)
				.hasMessageContaining(INVALID_LENGTH.getMessage());
		}
	}
}
