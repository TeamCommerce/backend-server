package com.commerce.backendserver.review.domain.additionalinfo;

import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

import com.commerce.backendserver.review.domain.Review;
import com.commerce.backendserver.review.domain.additionalinfo.constants.InfoName;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "t_additional_info")
@NoArgsConstructor(access = PROTECTED)
public class AdditionalInfo {

	@Id
	@Column(name = "additional_info_id")
	@GeneratedValue(strategy = IDENTITY)
	private Long id;

	@Enumerated(value = STRING)
	@Column(nullable = false)
	private InfoName infoName;

	@Column(nullable = false)
	private String infoValue;

	@JoinColumn(name = "review_id", updatable = false)
	@ManyToOne(fetch = LAZY)
	private Review review;

	//== Construct Method ==//
	@Builder
	private AdditionalInfo(InfoName infoName, String infoValue) {
		this.infoName = infoName;
		this.infoValue = infoValue;
	}

	public static AdditionalInfo of(InfoName infoName, String infoValue) {
		return AdditionalInfo.builder()
			.infoName(infoName)
			.infoValue(infoValue)
			.build();
	}

	public void registerReview(Review review) {
		this.review = review;
	}
}
