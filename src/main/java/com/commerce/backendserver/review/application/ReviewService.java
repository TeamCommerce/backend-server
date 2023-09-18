package com.commerce.backendserver.review.application;

import com.commerce.backendserver.global.exception.CommerceException;
import com.commerce.backendserver.image.application.ImageService;
import com.commerce.backendserver.product.domain.Product;
import com.commerce.backendserver.product.infra.persistence.ProductQueryRepository;
import com.commerce.backendserver.review.application.dto.request.CreateReviewRequest;
import com.commerce.backendserver.review.domain.Review;
import com.commerce.backendserver.review.infra.persistence.ReviewRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.commerce.backendserver.global.exception.error.GlobalError.GLOBAL_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

	private static final String REVIEW = "review";

	private final ReviewRepository reviewRepository;
	private final ImageService imageService;
	private final ProductQueryRepository productQueryRepository;

	public Long createReview(CreateReviewRequest request, Long writerId) {

		List<String> imageUrls = imageService.uploadImages(request.files(), REVIEW);

		Product product = productQueryRepository.findById(request.productId())
			.orElseThrow(() -> CommerceException.of(GLOBAL_NOT_FOUND));

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
}
