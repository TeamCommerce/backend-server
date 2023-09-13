package com.commerce.backendserver.review.presentation;

import com.commerce.backendserver.auth.infra.oauth.CustomOAuth2User;
import com.commerce.backendserver.review.application.ReviewService;
import com.commerce.backendserver.review.application.dto.request.CreateReviewRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<Void> registerReview(
            @Validated @ModelAttribute CreateReviewRequest request,
            @AuthenticationPrincipal CustomOAuth2User member
    ) {
        Long reviewId = reviewService.createReview(request, member.getId());

        return ResponseEntity
                .created(URI.create(String.format("/api/reviews/%s", reviewId)))
                .build();
    }
}

