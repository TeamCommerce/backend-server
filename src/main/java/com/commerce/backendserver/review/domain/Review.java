package com.commerce.backendserver.review.domain;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;
import static org.hibernate.annotations.OnDeleteAction.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.OnDelete;

import com.commerce.backendserver.global.auditing.BaseEntity;
import com.commerce.backendserver.image.domain.ReviewImage;
import com.commerce.backendserver.product.domain.Product;
import com.commerce.backendserver.review.domain.additionalinfo.AdditionalInfo;
import com.commerce.backendserver.review.domain.additionalinfo.AdditionalInfoList;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "t_review")
@NoArgsConstructor(access = PROTECTED)
public class Review extends BaseEntity {

	@OneToMany(
		mappedBy = "review",
		cascade = PERSIST,
		orphanRemoval = true)
	@OnDelete(action = CASCADE)
	private final List<ReviewImage> images = new ArrayList<>();

	@Id
	@Column(name = "review_id")
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String contents;

	@Embedded
	private Score score;

	@Embedded
	private AdditionalInfoList additionalInfoList;

	@JoinColumn(name = "product_id", nullable = false)
	@ManyToOne(fetch = LAZY)
	private Product product;

	private Long productOptionId;
	private Long writerId;

	//== Constructor Method ==//
	@Builder
	private Review(
		final String contents,
		final Integer score,
		final Set<String> stringInfoSet,
		final Product product,
		final Long productOptionId,
		final Long writerId,
		final List<String> imageUrls
	) {
		this.contents = contents;
		this.score = new Score(score);
		this.additionalInfoList = AdditionalInfoList.of(stringInfoSet, this);
		this.product = product;
		this.productOptionId = productOptionId;
		this.writerId = writerId;
		applyImages(imageUrls);
	}

	//== Static Factory Method ==//
	public static Review createReview(
		final String contents,
		final Integer score,
		final Set<String> stringInfoSet,
		final Product product,
		final Long productOptionId,
		final Long writerId,
		final List<String> imageUrls
	) {
		return Review.builder()
			.contents(contents)
			.score(score)
			.stringInfoSet(stringInfoSet)
			.product(product)
			.productOptionId(productOptionId)
			.writerId(writerId)
			.imageUrls(imageUrls)
			.build();
	}

	private void applyImages(List<String> imageUrls) {
		if (imageUrls == null) {
			return;
		}

		this.images.addAll(
			imageUrls.stream()
				.map(imageUrl -> ReviewImage.of(imageUrl, this))
				.toList()
		);
	}

	//== Utility Method ==//
	public List<AdditionalInfo> getAdditionalInfoList() {
		return additionalInfoList.getList();
	}

	public int getScore() {
		return score.getValue();
	}
}
