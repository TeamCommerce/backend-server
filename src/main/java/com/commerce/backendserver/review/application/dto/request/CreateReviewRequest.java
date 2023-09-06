package com.commerce.backendserver.review.application.dto.request;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.commerce.backendserver.review.application.utils.validator.ValidAdditionalInfo;
import com.commerce.backendserver.review.application.utils.validator.ValidContentsLength;
import com.commerce.backendserver.review.application.utils.validator.ValidScore;
import com.commerce.backendserver.review.domain.additionalinfo.AdditionalInfo;

public record CreateReviewRequest(
	@ValidScore
	Integer score,
	@ValidContentsLength
	String contents,
	Long productId,
	@ValidAdditionalInfo
	List<AdditionalInfoRequest> additionalInfoRequest,
	List<MultipartFile> files
) {
	public List<AdditionalInfo> additionalInfoList() {
		return additionalInfoRequest
			.stream()
			.map(AdditionalInfoRequest::toAdditionalInfo)
			.toList();
	}
}
