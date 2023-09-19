package com.commerce.backendserver.product.application.dto.response;

import com.commerce.backendserver.product.domain.option.ProductAdditionalOption;
import com.commerce.backendserver.product.domain.option.ProductColor;
import com.commerce.backendserver.product.domain.option.ProductOption;
import com.commerce.backendserver.product.domain.option.constants.ProductSize;
import com.commerce.backendserver.product.domain.option.constants.ProductStatus;

import java.util.List;

public record ProductOptionResponse(
        Long optionId,
        ProductColor color,
        ProductAdditionalOption additionalOption,
        Integer inventory,
        ProductStatus status,
        ProductSize size
) {
    public static List<ProductOptionResponse> toOptionResponseList(List<ProductOption> productOptions) {
        return productOptions.stream()
                .map(ProductOptionResponse::from)
                .toList();
    }

    private static ProductOptionResponse from(ProductOption productOption) {
        return new ProductOptionResponse(
                productOption.getId(),
                productOption.getColor(),
                productOption.getAdditionalOption(),
                productOption.getInventory(),
                productOption.getStatus(),
                productOption.getSize()
        );
    }
}
