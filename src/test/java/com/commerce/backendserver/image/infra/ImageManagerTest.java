package com.commerce.backendserver.image.infra;

import static com.commerce.backendserver.common.utils.FileMockingUtils.*;
import static com.commerce.backendserver.image.exception.ImageError.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.commerce.backendserver.common.base.MockTestBase;
import com.commerce.backendserver.global.exception.CommerceException;

@DisplayName("ImageManager Test] (Infra layer)")
class ImageManagerTest extends MockTestBase {

	private static final String REVIEW = "review";
	private static final String BUCKET_NAME = "bucketName";
	private static final String CLOUD_DOMAIN = "https://cloud-domain";

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

			final URL mockUrl = new URL(createUploadLink());
			given(amazonS3Client.getUrl(eq(BUCKET_NAME), any(String.class)))
				.willReturn(mockUrl);

			List<MultipartFile> files = createMockMultipartFiles();

			//when
			List<String> result = imageManager.uploadFiles(files, REVIEW);

			//then
			assertAll(
				() -> assertThat(result).hasSize(2),
				() -> {
					assert result != null;
					result.forEach(
						url -> assertThat(url).isEqualTo(mockUrl.toString()));
				}
			);
		}

		@Test
		@DisplayName("fail by empty file")
		void failByEmptyFile() {
			//given
			List<MultipartFile> emptyFiles = List.of(createEmptyFile());

			//when, then
			assertThatThrownBy(() -> imageManager.uploadFiles(emptyFiles, REVIEW))
				.isInstanceOf(CommerceException.class)
				.hasMessageContaining(EMPTY_FILE.getMessage());
		}
	}

	private String createUploadLink() {
		return String.format(
			"%s/%s/%s/%s",
			CLOUD_DOMAIN,
			BUCKET_NAME,
			REVIEW,
			UUID.randomUUID() + ".jpg"
		);
	}
}
