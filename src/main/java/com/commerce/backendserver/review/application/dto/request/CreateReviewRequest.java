package com.commerce.backendserver.review.application.dto.request;

import java.util.List;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

public record CreateReviewRequest(
	Integer score,
	String contents,
	Long productId,
	Long productOptionId,
	Set<String> additionalInfo,
	List<MultipartFile> files
) {
}
