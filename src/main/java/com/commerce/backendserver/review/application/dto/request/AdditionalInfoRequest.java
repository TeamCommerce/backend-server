package com.commerce.backendserver.review.application.dto.request;

import static com.commerce.backendserver.review.domain.additionalinfo.constants.InfoName.*;

import com.commerce.backendserver.review.domain.additionalinfo.AdditionalInfo;

public record AdditionalInfoRequest(
	String infoName,
	String infoValue
) {

	public AdditionalInfo toAdditionalInfo() {
		return AdditionalInfo.of(matchInfoName(infoName), infoValue);
	}
}
