package com.commerce.backendserver.image.infra;

import static com.commerce.backendserver.image.exception.ImageError.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.commerce.backendserver.global.exception.CommerceException;

@DisplayName("[BuketMetadata Test] - Infra layer")
class BuketMetadataTest {

	@Nested
	@DisplayName("[generateFilename]")
	class generateFilenameTest {

		private final static String FILENAME = "filename";

		@DisplayName("[success]")
		@ParameterizedTest(name = "{0}")
		@ValueSource(strings = {"review", "product"})
		void success(String type) {
			//when
			String result = BucketMetadata.generateFilename(FILENAME, type);

			//then
			String[] splitResult = result.split("/");
			assertAll(
				() -> assertThat(splitResult).hasSize(2),
				() -> assertThat(splitResult[0]).isEqualTo(type),
				() -> assertThat(splitResult[1]).isEqualTo(FILENAME)
			);
		}

		@Test
		@DisplayName("[Fail] 존재하지 않는 타입으로 인해 실패")
		void failWhenPresentNotExistType() {
			//when
			ThrowingCallable throwingCallable = () -> BucketMetadata.generateFilename(FILENAME, "hello");

			//then
			assertThatThrownBy(throwingCallable)
				.isInstanceOf(CommerceException.class)
				.hasMessageContaining(INVALID_IMAGE_TYPE.getMessage());
		}
	}
}
