package com.commerce.backendserver.global.exception.error;

import static com.commerce.backendserver.global.exception.error.GlobalError.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

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
			ErrorResponse result = ErrorResponse.of(Optional.empty());

			//then

			assertAll(
				() -> assertThat(result.getErrorCode()).isEqualTo(INVALID_REQUEST_PARAM.getCode()),
				() -> assertThat(result.getTimeStamp()).isNotBlank(),
				() -> assertThat(result.getErrorMessage()).isEqualTo(INVALID_REQUEST_PARAM.getMessage())
			);
		}
	}
}
