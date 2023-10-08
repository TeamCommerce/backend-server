package com.commerce.backendserver.review.infra.persistence;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.commerce.backendserver.product.domain.option.constants.ProductSize;
import com.commerce.backendserver.review.domain.Review;
import com.commerce.backendserver.review.domain.ReviewRepository;
import com.commerce.backendserver.review.infra.persistence.query.ReviewQueryDslRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReviewRepositoryAdapter implements ReviewRepository {

	private final ReviewJpaRepository reviewJpaRepository;
	private final ReviewQueryDslRepository reviewQueryDslRepository;

	@Override
	public Review save(Review review) {
		return reviewJpaRepository.save(review);
	}

	@Override
	public List<Review> findReviewByStatisticCondition(
		final Set<String> engColorNames,
		final Set<ProductSize> sizes,
		final Set<String> additionalOptions,
		final Set<Integer> scores,
		final Set<String> additionalInfoList
	) {
		return reviewQueryDslRepository.findReviewByStatisticCondition(
			engColorNames,
			sizes,
			additionalOptions,
			scores,
			additionalInfoList
		);
	}

}
