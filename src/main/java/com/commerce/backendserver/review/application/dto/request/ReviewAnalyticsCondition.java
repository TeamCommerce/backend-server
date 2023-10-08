package com.commerce.backendserver.review.application.dto.request;

import com.commerce.backendserver.product.domain.option.constants.ProductSize;

import java.util.Set;

public record ReviewAnalyticsCondition(
        Set<String> engColorNames,
        Set<ProductSize> sizes,
        Set<String> additionalOptions,
        Set<Integer> scores,
        Set<String> additionalInfoList
) {
}
