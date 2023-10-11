package com.commerce.backendserver.review.presentation;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.commerce.backendserver.global.annotation.FetchAuthInfo;
import com.commerce.backendserver.global.resolver.dto.AuthInfo;
import com.commerce.backendserver.review.application.ReviewAnalyticsService;
import com.commerce.backendserver.review.application.ReviewService;
import com.commerce.backendserver.review.application.dto.request.CreateReviewRequest;
import com.commerce.backendserver.review.application.dto.request.ReviewAnalyticsCondition;
import com.commerce.backendserver.review.application.dto.response.ReviewStatistics;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

	private final ReviewService reviewService;
	private final ReviewAnalyticsService reviewAnalyticsService;

	@PostMapping
	public ResponseEntity<Void> registerReview(
		@Validated @ModelAttribute CreateReviewRequest request,
		@FetchAuthInfo AuthInfo authInfo
	) {
		Long reviewId = reviewService.createReview(request, authInfo.memberId());

		return ResponseEntity
			.created(URI.create(String.format("/api/reviews/%s", reviewId)))
			.build();
	}

	@GetMapping("/statistics")
	public ResponseEntity<ReviewStatistics> getReviewStatistics(
		@ModelAttribute ReviewAnalyticsCondition condition
	) {
		ReviewStatistics result = reviewAnalyticsService.getReviewStatistics(condition);

		return ResponseEntity.ok(result);
	}
}

