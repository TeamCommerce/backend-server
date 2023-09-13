package com.commerce.backendserver.image.infra;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("BuketMetadata Test] (Infra layer)")
class BuketMetadataTest {

    @DisplayName("[generateFilename method]")
    @ParameterizedTest(name = "{0}")
    @ValueSource(strings = {"review", "product"})
    void generateFilenameTest(String type) {
        //given
        final String filename = "filename";

        //when
        String result = BucketMetadata.generateFilename(filename, type);

        //then
        String[] splitResult = result.split("/");
        assertAll(
                () -> assertThat(splitResult).hasSize(2),
                () -> assertThat(splitResult[0]).isEqualTo(type),
                () -> assertThat(splitResult[1]).isEqualTo(filename)
        );
    }
}
