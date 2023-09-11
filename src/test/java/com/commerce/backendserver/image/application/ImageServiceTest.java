package com.commerce.backendserver.image.application;

import static com.commerce.backendserver.common.utils.FileMockingUtils.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.web.multipart.MultipartFile;

import com.commerce.backendserver.common.base.MockTestBase;
import com.commerce.backendserver.image.infra.ImageManager;

@DisplayName("ImageService Test] (Application layer)")
class ImageServiceTest extends MockTestBase {

	private static final String TYPE = "review";

	@InjectMocks
	ImageService imageService;

	@Mock
	ImageManager imageManager;

	@Test
	@DisplayName("[uploadImages method]")
	void uploadImagesTest() throws IOException {
		//given
		List<MultipartFile> files = createMockMultipartFiles();

		List<String> mockUrls = List.of("url1", "url2");
		given(imageManager.uploadFiles(files, TYPE))
			.willReturn(mockUrls);

		//when
		List<String> result = imageService.uploadImages(files, TYPE);

		//then
		assert result != null;
		assertAll(
			() -> assertThat(result).hasSize(2),
			() -> assertMatchUrls(result, mockUrls)
		);
	}

	private void assertMatchUrls(List<String> actual, List<String> expected) {
		IntStream.range(0, 2)
			.forEach(i -> assertThat(actual.get(i)).isEqualTo(expected.get(i)));
	}
}
