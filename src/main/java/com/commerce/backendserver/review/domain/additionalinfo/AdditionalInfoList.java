package com.commerce.backendserver.review.domain.additionalinfo;

import static com.commerce.backendserver.review.domain.additionalinfo.constants.InfoName.*;
import static com.commerce.backendserver.review.exception.ReviewError.*;
import static jakarta.persistence.CascadeType.*;
import static java.util.Comparator.*;
import static lombok.AccessLevel.*;
import static org.hibernate.annotations.OnDeleteAction.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.OnDelete;

import com.commerce.backendserver.global.exception.CommerceException;
import com.commerce.backendserver.review.domain.Review;
import com.commerce.backendserver.review.domain.additionalinfo.constants.InfoName;

import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
		final Set<String> infoSet,
		final Review review
	) {
		//null inSet 이 null 인 경우는 예외가 아닌 통과
		if (infoSet == null) {
			return;
		}

		validateAdditionalInfo(infoSet);
		List<AdditionalInfo> additionalInfoList = toAdditionalInfoList(infoSet);
		applyAdditionalInfo(additionalInfoList, review);
	}

	public static AdditionalInfoList of(
		final Set<String> infoSet,
		final Review review
	) {
		return AdditionalInfoList.builder()
			.infoSet(infoSet)
			.review(review)
			.build();
	}

	private List<AdditionalInfo> toAdditionalInfoList(final Set<String> infoSet) {
		return infoSet.stream()
			.map(info -> {
				String[] splitInfo = info.split("/");
				validateInfoFormat(splitInfo);

				InfoName infoName = getInfoName(splitInfo[0]);
				String infoValue = splitInfo[1];
				infoName.validateInfoNameType(infoValue);

				return AdditionalInfo.of(infoName, infoValue);
			})
			.sorted(COMPARATOR)
			.toList();
	}

	private void applyAdditionalInfo(
		final List<AdditionalInfo> additionalInfoList,
		final Review review
	) {
		additionalInfoList.forEach(additionalInfo -> additionalInfo.registerReview(review));
		this.list.addAll(additionalInfoList);
	}

	private void validateAdditionalInfo(final Set<String> infoSet) {
		infoSet.forEach(target -> {
			String[] splitInfo = target.split("/");
			validateInfoFormat(splitInfo);

			InfoName infoName = getInfoName(splitInfo[0]);
			String infoValue = splitInfo[1];
			infoName.validateInfoNameType(infoValue);
		});
	}

	private void validateInfoFormat(final String[] splitInfo) {
		if (splitInfo.length != 2) {
			throw CommerceException.of(INVALID_ADDITIONAL_INFO);
		}
	}
}
