package com.commerce.backendserver.product.application.dto.response;

import com.commerce.backendserver.product.domain.option.ProductAdditionalOption;
import com.commerce.backendserver.product.domain.option.ProductColor;
import com.commerce.backendserver.product.domain.option.constants.ProductSize;
import com.commerce.backendserver.product.domain.option.constants.ProductStatus;

public record ProductOptionResponse(
	Long optionId,
	ProductColor color,
	ProductAdditionalOption additionalOption,
	Integer inventory,
	ProductStatus status,
	ProductSize size
) {
}
