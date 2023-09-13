package com.commerce.backendserver.review.domain.additionalinfo;

import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.OnDelete;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static com.commerce.backendserver.review.domain.additionalinfo.constants.InfoName.*;
import static jakarta.persistence.CascadeType.PERSIST;
import static java.util.Comparator.*;
import static lombok.AccessLevel.PROTECTED;
import static org.hibernate.annotations.OnDeleteAction.CASCADE;

import com.commerce.backendserver.review.domain.Review;

@Getter
@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class AdditionalInfoList {

	private static final Comparator<AdditionalInfo> COMPARATOR =
		comparingInt(info -> info.getInfoName().getOrder());

	@OneToMany(
		mappedBy = "review",
		cascade = PERSIST,
		orphanRemoval = true)
	@OnDelete(action = CASCADE)
	private final List<AdditionalInfo> list = new ArrayList<>();

	////== Construct Method ==//
	@Builder
	private AdditionalInfoList(
		Set<String> stringInfoSet,
		Review review
	) {
		List<AdditionalInfo> additionalInfoList = toAdditionalInfoList(stringInfoSet);
		applyAdditionalInfo(additionalInfoList, review);
	}

	public static AdditionalInfoList of(
		Set<String> stringInfoSet,
		Review review
	) {
		return AdditionalInfoList.builder()
			.stringInfoSet(stringInfoSet)
			.review(review)
			.build();
	}

	private List<AdditionalInfo> toAdditionalInfoList(Set<String> stringInfoSet) {
		if (stringInfoSet == null)
			return new ArrayList<>();

		List<AdditionalInfo> additionalInfoList = new ArrayList<>(
			stringInfoSet.stream()
				.map(info -> {
					String[] splitInfo = info.split("/");

					return AdditionalInfo.of(
						matchInfoName(splitInfo[0]),
						splitInfo[1]);
				}).toList());

		additionalInfoList.sort(COMPARATOR);
		return additionalInfoList;
	}

	private void applyAdditionalInfo(
		List<AdditionalInfo> additionalInfoList,
		Review review
	) {
		additionalInfoList.forEach(additionalInfo -> additionalInfo.registerReview(review));
		this.list.addAll(additionalInfoList);
	}
}
