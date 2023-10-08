package com.commerce.backendserver.review.application.utils;

import com.commerce.backendserver.common.fixture.ReviewFixture;
import com.commerce.backendserver.review.application.dto.response.ReviewStatistics;
import com.commerce.backendserver.review.domain.Review;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static com.commerce.backendserver.review.utils.ReviewAsserter.assertReviewStatistic;

@DisplayName("[ReviewAnalyzer Test] - Application layer")
class ReviewAnalyzerTest {

    private final ReviewAnalyzer reviewAnalyzer = new ReviewAnalyzer(new RatioAnalyzer());

    @Test
    @DisplayName("[analyzeReview]")
    void analyzeReviewTest() {
        //given
        List<Review> reviews = Arrays.stream(ReviewFixture.values())
                .map(fixture -> fixture.toEntity(null, null, null))
                .toList();

        //when
        ReviewStatistics result = reviewAnalyzer.analyzeReview(reviews);

        //then
        assertReviewStatistic(result, reviews);
    }
}
