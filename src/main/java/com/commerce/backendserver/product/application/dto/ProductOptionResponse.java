package com.commerce.backendserver.product.application.dto;

import com.commerce.backendserver.product.domain.option.constants.ProductSize;
import com.commerce.backendserver.product.domain.option.constants.ProductStatus;

public record ProductOptionResponse(
        String korColorName,
        String engColorName,
        String colorCode,
        ProductSize size,
        String optionType,
        String optionValue,
        Integer additionalFee,
        Integer inventory,
        ProductStatus productStatus
) {
}
