package com.commerce.backendserver.review.application.dto.request;

import java.util.Set;

import com.commerce.backendserver.product.domain.option.constants.ProductSize;

public record ReviewAnalyticsCondition(
	Set<String> engColorNames,
	Set<ProductSize> sizes,
	Set<String> additionalOptions,
	Set<Integer> scores,
	Set<String> additionalInfoList
) {
}
