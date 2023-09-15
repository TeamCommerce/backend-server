package com.commerce.backendserver.review.domain.likereview;

import static lombok.AccessLevel.*;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "t_like_review")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeReview {

    @Id
    @Column(name = "like_review_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private Long memberId;

    @Column(nullable = false, updatable = false)
    private Long reviewId;

    @Builder(access = PRIVATE)
    private LikeReview(Long memberId, Long reviewId) {
        this.memberId = memberId;
        this.reviewId = reviewId;
    }

    public static LikeReview of(Long memberId, Long reviewId) {
        return LikeReview.builder()
            .memberId(memberId)
            .reviewId(reviewId)
            .build();
    }
}