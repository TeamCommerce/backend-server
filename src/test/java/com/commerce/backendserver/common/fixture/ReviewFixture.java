package com.commerce.backendserver.common.fixture;

import com.commerce.backendserver.common.utils.FileMockingUtils;
import com.commerce.backendserver.product.domain.Product;
import com.commerce.backendserver.review.application.dto.request.CreateReviewRequest;
import com.commerce.backendserver.review.domain.Review;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.List;
import java.util.Set;

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

    public CreateReviewRequest toCreateRequest() throws IOException {
        return new CreateReviewRequest(
                score,
                contents,
                1L,
                stringInfoSet,
                FileMockingUtils.createMockMultipartFiles()
        );
    }
}
