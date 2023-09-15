package com.commerce.backendserver.global.exception.error;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("[ErrorResponse Test] (Additional Test)")
class ErrorResponseTest {

	@Nested
	@DisplayName("[of method]")
	class ofTest {
		@Test
		@DisplayName("when fieldError is null")
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
