package com.commerce.backendserver.global.exception.error;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@DisplayName("[ErrorResponse Test] - Global)")
class
ErrorResponseTest {

	@Nested
	@DisplayName("[of]")
	class ofTest {

		@Test
		@DisplayName("[Fail] 필드 에러가 Null일 때 실패]")
		void whenFieldErrorIsNull() {
			//when
			ErrorResponse result = ErrorResponse.of(BAD_REQUEST, Optional.empty());

			//then
			assertAll(
				() -> assertThat(result.getErrorCode()).isEqualTo(BAD_REQUEST.value()),
				() -> assertThat(result.getTimeStamp()).isNotBlank(),
				() -> assertThat(result.getErrorMessage()).isEqualTo("Invalid Param")
			);
		}
	}
}
