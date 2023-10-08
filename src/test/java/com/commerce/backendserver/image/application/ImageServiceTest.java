package com.commerce.backendserver.image.application;

import com.commerce.backendserver.common.base.MockTestBase;
import com.commerce.backendserver.image.infra.ImageManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;

import static com.commerce.backendserver.common.utils.FileMockingUtils.createMockMultipartFiles;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;

@DisplayName("[ImageService Test] - Application layer")
class ImageServiceTest extends MockTestBase {

    private static final String TYPE = "review";

    @InjectMocks
    private ImageService imageService;

    @Mock
    private ImageManager imageManager;

    @Test
    @DisplayName("[uploadImages]")
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
