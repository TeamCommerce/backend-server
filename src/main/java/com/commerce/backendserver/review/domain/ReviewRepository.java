package com.commerce.backendserver.review.domain;

import java.util.List;
import java.util.Set;

import com.commerce.backendserver.product.domain.option.constants.ProductSize;

public interface ReviewRepository {

	Review save(final Review review);

	List<Review> findReviewByStatisticCondition(
		final Set<String> engColorNames,
		final Set<ProductSize> sizes,
		final Set<String> additionalOptions,
		final Set<Integer> scores,
		final Set<String> additionalInfoList
	);
}
