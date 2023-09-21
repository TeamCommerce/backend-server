package com.commerce.backendserver.common.fixture;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.commerce.backendserver.common.utils.FileMockingUtils;
import com.commerce.backendserver.product.domain.Product;
import com.commerce.backendserver.review.application.dto.request.CreateReviewRequest;
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

	B(
		"contentsB",
		4,
		Set.of("HEIGHT/172", "SIZE/Medium", "WEIGHT/60"),
		List.of("helloA.jpg", "helloB.png")
	),

	C(
		"contentsC",
		2,
		Set.of("HEIGHT/156", "SIZE/Small", "WEIGHT/40"),
		List.of("helloA.jpg", "helloB.png")
	);

	private final String contents;
	private final Integer score;
	private final Set<String> stringInfoSet;
	private final List<String> imageUrls;

	public Review toEntity(Product product, Long writerId, Long productOptionId) {
		return Review.createReview(
			contents,
			score,
			stringInfoSet,
			product,
			productOptionId,
			writerId,
			imageUrls
		);
	}

	public CreateReviewRequest toCreateRequest() throws IOException {
		return new CreateReviewRequest(
			score,
			contents,
			1L,
			1L,
			stringInfoSet,
			FileMockingUtils.createMockMultipartFiles()
		);
	}
}
