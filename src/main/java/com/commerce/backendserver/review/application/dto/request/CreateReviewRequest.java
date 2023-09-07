package com.commerce.backendserver.review.application.dto.request;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.commerce.backendserver.review.application.utils.validator.ValidAdditionalInfo;
import com.commerce.backendserver.review.application.utils.validator.ValidContentsLength;
import com.commerce.backendserver.review.application.utils.validator.ValidScore;

public record CreateReviewRequest(
	@ValidScore
	Integer score,

	@ValidContentsLength
	String contents,

	Long productId,

	@ValidAdditionalInfo
	Set<String> additionalInfo,

	List<MultipartFile> files
) {
	public CreateReviewRequest(
		Integer score,
		String contents,
		Long productId,
		Set<String> additionalInfo,
		List<MultipartFile> files
	) {
		this.score = score;
		this.contents = contents;
		this.productId = productId;
		this.additionalInfo = new HashSet<>();
		this.files = new ArrayList<>();
		if (additionalInfo != null) {
			this.additionalInfo.addAll(additionalInfo);
		}
		if (files != null) {
			this.files.addAll(files);
		}
	}
}
