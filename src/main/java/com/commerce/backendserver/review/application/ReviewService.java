package com.commerce.backendserver.review.application;

import static com.commerce.backendserver.global.exception.error.GlobalError.*;
import static com.commerce.backendserver.review.exception.ReviewError.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.commerce.backendserver.global.exception.CommerceException;
import com.commerce.backendserver.image.application.ImageService;
import com.commerce.backendserver.product.domain.Product;
import com.commerce.backendserver.product.domain.ProductRepository;
import com.commerce.backendserver.product.domain.option.ProductOption;
import com.commerce.backendserver.review.application.dto.request.CreateReviewRequest;
import com.commerce.backendserver.review.domain.Review;
import com.commerce.backendserver.review.infra.persistence.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

	private static final String REVIEW = "review";

	private final ReviewRepository reviewRepository;
	private final ImageService imageService;
	private final ProductRepository productRepository;

	public Long createReview(CreateReviewRequest request, Long writerId) {

		List<String> imageUrls = imageService.uploadImages(request.files(), REVIEW);

		Product product = productRepository.findDistinctWithOptionsById(request.productId())
			.orElseThrow(() -> CommerceException.of(GLOBAL_NOT_FOUND));

		checkMatchingProductOptionIdToProduct(request.productOptionId(), product.getOptions());

		Review review = Review.createReview(
			request.contents(),
			request.score(),
			request.additionalInfo(),
			product,
			request.productId(),
			writerId,
			imageUrls
		);

		return reviewRepository.save(review).getId();
	}

	private void checkMatchingProductOptionIdToProduct(Long productOptionId, List<ProductOption> options) {
		boolean isPresent = options.stream()
			.map(ProductOption::getId)
			.anyMatch(productOptionId::equals);

		if (!isPresent) {
			throw CommerceException.of(NOT_MATCH_PRODUCT_OPTION_ID);
		}
	}
}
