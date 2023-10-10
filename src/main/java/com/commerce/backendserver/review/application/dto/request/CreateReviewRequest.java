package com.commerce.backendserver.review.application.dto.request;

import java.util.List;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;

public record CreateReviewRequest(
	@NotNull Integer score,
	String contents,
	@NotNull Long productId,
	@NotNull Long productOptionId,
	Set<String> additionalInfo,
	List<MultipartFile> files
) {
}
