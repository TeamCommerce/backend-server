package com.commerce.backendserver.review.domain;

import com.commerce.backendserver.global.auditing.BaseEntity;
import com.commerce.backendserver.image.domain.ReviewImage;
import com.commerce.backendserver.product.domain.Product;
import com.commerce.backendserver.review.domain.additionalinfo.AdditionalInfoList;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.Check;
import org.hibernate.annotations.OnDelete;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;
import static org.hibernate.annotations.OnDeleteAction.CASCADE;

@Entity
@Getter
@Table(name = "t_review")
@NoArgsConstructor(access = PROTECTED)
public class Review extends BaseEntity {

    @Id
    @Column(name = "review_id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    @Check(constraints = "score >= 1 AND score <= 5")
    private Integer score;

    @Embedded
    private AdditionalInfoList additionalInfoList;

    @JoinColumn(name = "product_id")
    @ManyToOne(fetch = LAZY)
    private Product product;

    private Long writerId;

    @OneToMany(
            mappedBy = "review",
            cascade = PERSIST,
            orphanRemoval = true)
    @OnDelete(action = CASCADE)
    private final List<ReviewImage> images = new ArrayList<>();

    //== Construct Method ==//
    @Builder
    private Review(
        String contents,
        Integer score,
        Set<String> stringInfoSet,
        Product product,
        Long writerId,
        List<String> imageUrls
    ) {
        this.contents = contents;
        this.score = score;
        this.additionalInfoList = AdditionalInfoList.of(stringInfoSet, this);
        this.product = product;
        this.writerId = writerId;
        applyImages(imageUrls);
    }

    private void applyImages(List<String> imageUrls) {
        this.images.addAll(
            imageUrls.stream()
            .map(imageUrl -> ReviewImage.of(imageUrl, this))
            .toList()
        );
    }

    public static Review createReview(
        String contents,
        Integer score,
        Set<String> stringInfoSet,
        Product product,
        Long writerId,
        List<String> imageUrls
    ) {
        return Review.builder()
            .contents(contents)
            .score(score)
            .stringInfoSet(stringInfoSet)
            .product(product)
            .writerId(writerId)
            .imageUrls(imageUrls)
            .build();
    }
}
