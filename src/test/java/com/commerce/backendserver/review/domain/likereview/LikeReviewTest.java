package com.commerce.backendserver.review.domain.likereview;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("[LikeReview Test] - Domain layer")
class LikeReviewTest {

    @Test
    @DisplayName("[of]")
    void ofTest() {
        //given
        final Long memberId = 1L;
        final Long reviewId = 1L;

        //when
        LikeReview result = LikeReview.of(memberId, reviewId);

        //then
        assertAll(
                () -> assertThat(result.getMemberId()).isEqualTo(memberId),
                () -> assertThat(result.getReviewId()).isEqualTo(reviewId)
        );
    }
}
