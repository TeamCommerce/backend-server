package com.commerce.backendserver.review.stub;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.springframework.test.util.ReflectionTestUtils;

import com.commerce.backendserver.common.fixture.ReviewFixture;
import com.commerce.backendserver.product.domain.option.constants.ProductSize;
import com.commerce.backendserver.review.domain.Review;
import com.commerce.backendserver.review.domain.ReviewRepository;

public class ReviewRepositoryStub implements ReviewRepository {

	@Override
	public Review save(final Review review) {
		ReflectionTestUtils.setField(review, "id", 1L);
		return review;
	}

	@Override
	public List<Review> findReviewByStatisticCondition(
		final Set<String> engColorNames,
		final Set<ProductSize> sizes,
		final Set<String> additionalOptions,
		final Set<Integer> scores, Set<String> additionalInfoList
	) {
		return Arrays.stream(ReviewFixture.values())
			.map(fixture -> fixture.toEntity(null, null, null))
			.toList();
	}
}
