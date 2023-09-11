package com.commerce.backendserver.common.fixture;

import java.util.List;
import java.util.Set;

import com.commerce.backendserver.product.domain.Product;
import com.commerce.backendserver.review.domain.Review;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReviewFixture {
	A(
		"contentsA",
		3,
		Set.of("HEIGHT/172", "SIZE/Large"),
		List.of("helloA.jpg", "helloB.png")
	),
	;

	private final String contents;
	private final Integer score;
	private final Set<String> stringInfoSet;
	private final List<String> imageUrls;

	public Review toEntity(Product product, Long writerId) {
		return Review.createReview(
			contents,
			score,
			stringInfoSet,
			product,
			writerId,
			imageUrls
		);
	}
}
