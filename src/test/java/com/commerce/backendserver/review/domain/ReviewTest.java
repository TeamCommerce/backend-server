package com.commerce.backendserver.review.domain;

import static com.commerce.backendserver.product.fixture.ProductFixture.VALID_PRODUCT;
import static java.util.Comparator.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import com.commerce.backendserver.product.fixture.ProductFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.commerce.backendserver.common.fixture.ReviewFixture;
import com.commerce.backendserver.image.domain.Image;
import com.commerce.backendserver.product.domain.Product;
import com.commerce.backendserver.review.domain.additionalinfo.AdditionalInfo;
import com.commerce.backendserver.review.domain.additionalinfo.constants.InfoName;

@DisplayName("[Review Test] (Domain layer)")
public class ReviewTest {

	@Nested
	@DisplayName("[createReview method]")
	class createReviewTest {

		//given
		private final Product product = VALID_PRODUCT.toEntity(null);
		private final Long writerId = 1L;

		@Test
		@DisplayName("imageUrl is not null")
		void imageUrlsIsNotNull() {
			//given
			final ReviewFixture fixture = ReviewFixture.A;

			//when
			Review result = fixture.toEntity(product, writerId);

			//then
			assertAll(
				() -> assertThat(result.getScore()).isEqualTo(fixture.getScore()),
				() -> assertThat(result.getContents()).isEqualTo(fixture.getContents()),
				() -> assertThat(result.getWriterId()).isEqualTo(writerId),
				() -> assertThat(result.getProduct()).isEqualTo(product),
				() -> {
					List<String> actual = result.getImages().stream().map(Image::getUrl).toList();
					assertThat(actual).hasSameSizeAs(fixture.getImageUrls());
					assertThat(actual).containsAll(fixture.getImageUrls());
				},
				() -> {
					List<AdditionalInfo> actual = result.getAdditionalInfoList();
					List<AdditionalInfo> expected = generateExpectedAdditionalInfo(fixture.getStringInfoSet());

					assertAdditionalInfoMatching(actual, expected);
				}
			);
		}

		@Test
		@DisplayName("imageUrls is null")
		void ImageUrlsIsNull() {
			//when
			Review result = Review.createReview(
				null,
				null,
				null,
				null,
				null,
				null
			);

			//then
			assertThat(result.getImages()).isEmpty();
		}
	}

	private List<AdditionalInfo> generateExpectedAdditionalInfo(Set<String> stringInfoSet) {
		List<AdditionalInfo> expected = new ArrayList<>(
			stringInfoSet.stream()
				.map(info -> {
					String[] split = info.split("/");
					return AdditionalInfo.of(InfoName.valueOf(split[0]), split[1]);
				}).toList()
		);

		expected.sort(comparingInt(info -> info.getInfoName().getOrder()));

		return expected;
	}

	private void assertAdditionalInfoMatching(
		List<AdditionalInfo> actual,
		List<AdditionalInfo> expected
	) {
		assertThat(actual).hasSameSizeAs(expected);

		IntStream.range(0, actual.size())
			.forEach(i -> {
				AdditionalInfo actualInfo = actual.get(i);
				AdditionalInfo expectedInfo = expected.get(i);
				assertAll(
					() -> assertThat(actualInfo.getInfoName()).isEqualTo(expectedInfo.getInfoName()),
					() -> assertThat(actualInfo.getInfoValue()).isEqualTo(expectedInfo.getInfoValue())
				);
			});
	}
}
