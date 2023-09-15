package com.commerce.backendserver.image.infra;

import static com.commerce.backendserver.common.utils.FileMockingUtils.*;
import static com.commerce.backendserver.common.utils.S3LinkUtils.*;
import static com.commerce.backendserver.image.exception.ImageError.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.commerce.backendserver.common.base.MockTestBase;
import com.commerce.backendserver.global.exception.CommerceException;

@DisplayName("ImageManager Test] (Infra layer)")
class ImageManagerTest extends MockTestBase {

	private static final String REVIEW = "review";
	private static final String BUCKET_NAME = "bucketName";

	@Mock
	AmazonS3Client amazonS3Client;

	ImageManager imageManager;

	@BeforeEach
	void setUp() {
		imageManager = new ImageManager(amazonS3Client, BUCKET_NAME);
	}

	@Nested
	@DisplayName("[uploadFiles method]")
	class uploadFiles {

		@Test
		@DisplayName("success")
		void success() throws IOException {
			//given
			given(amazonS3Client.putObject(any(PutObjectRequest.class)))
				.willReturn(null);

			final URL mockUrl = new URL(createUploadLink(REVIEW));
			given(amazonS3Client.getUrl(eq(BUCKET_NAME), any(String.class)))
				.willReturn(mockUrl);

			List<MultipartFile> files = createMockMultipartFiles();

			//when
			List<String> notNullFileResult = imageManager.uploadFiles(files, REVIEW);
			List<String> nullFileResult = imageManager.uploadFiles(null, REVIEW);

			//then
			assertAll(
				() -> assertThat(nullFileResult).isEmpty(),
				() -> assertThat(notNullFileResult).hasSize(2),
				() -> {
					assert notNullFileResult != null;
					notNullFileResult.forEach(
						url -> assertThat(url).isEqualTo(mockUrl.toString()));
				}
			);
		}

		@Test
		@DisplayName("fail by empty file")
		void failByEmptyFile() {
			//given
			List<MultipartFile> emptyFiles = List.of(createEmptyFile());

			//when
			ThrowingCallable throwingCallable = () -> imageManager.uploadFiles(emptyFiles, REVIEW);

			//then
			assertThatThrownBy(throwingCallable)
				.isInstanceOf(CommerceException.class)
				.hasMessageContaining(EMPTY_FILE.getMessage());
		}

		@Test
		@DisplayName("fail by file upload")
		void failByFileUpload() throws IOException {
			//given
			MultipartFile mockFile = Mockito.mock(MultipartFile.class);

			given(mockFile.getOriginalFilename()).willReturn("filename.jpg");
			given(mockFile.isEmpty()).willReturn(false);
			given(mockFile.getInputStream()).willThrow(new IOException());

			List<MultipartFile> files = List.of(mockFile);

			//when
			ThrowingCallable throwingCallable = () -> imageManager.uploadFiles(files, REVIEW);

			//then
			assertThatThrownBy(throwingCallable)
				.isInstanceOf(CommerceException.class)
				.hasMessageContaining(UPLOAD_FAIL.getMessage());
		}
	}
}
