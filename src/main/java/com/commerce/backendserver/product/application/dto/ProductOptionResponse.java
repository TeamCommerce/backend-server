package com.commerce.backendserver.product.application.dto;

import com.commerce.backendserver.product.domain.option.ProductOption;
import com.commerce.backendserver.product.domain.option.ProductSelectionOption;
import com.commerce.backendserver.product.domain.option.constants.ProductColor;
import com.commerce.backendserver.product.domain.option.constants.ProductSize;
import com.commerce.backendserver.product.domain.option.constants.ProductStatus;

import java.util.List;

public record ProductOptionResponse(
        Long optionId,
        ProductColor color,
        ProductSelectionOption selectionOption,
        Integer inventory,
        ProductStatus status,
        ProductSize size
) {

    private static ProductOptionResponse toSingleResponse(
            final ProductOption productOption
    ) {
        return new ProductOptionResponse(
                productOption.getId(),
                productOption.getColor(),
                productOption.getSelectionOption(),
                productOption.getInventory(),
                productOption.getStatus(),
                productOption.getSize()
        );
    }

    public static List<ProductOptionResponse> toResponse(
            List<ProductOption> productOptions
    ) {
        return productOptions.stream()
                .map(ProductOptionResponse::toSingleResponse)
                .toList();
    }
}
