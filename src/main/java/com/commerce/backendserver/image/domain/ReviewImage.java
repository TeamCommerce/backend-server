package com.commerce.backendserver.image.domain;

import static jakarta.persistence.FetchType.*;
import static lombok.AccessLevel.*;

import com.commerce.backendserver.review.domain.Review;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@DiscriminatorValue("REVIEW")
@NoArgsConstructor(access = PROTECTED)
public class ReviewImage extends Image {

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "review_id")
	private Review review;

	@Builder
	private ReviewImage(
		String url,
		Review review
	) {
		super(url);
		this.review = review;
	}

	public static ReviewImage of(
		String url,
		Review review
	) {
		return ReviewImage.builder()
			.url(url)
			.review(review)
			.build();
	}
}

