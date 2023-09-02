package com.commerce.backendserver.review.domain.likereview;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeReview {

    @Id
    @Column(name = "like_review_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(nullable = false, updatable = false)
    private Long memberId;

    @JoinColumn(nullable = false, updatable = false)
    private Long reviewId;
}
