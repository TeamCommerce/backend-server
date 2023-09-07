package com.commerce.backendserver.product.application.dto;

import com.commerce.backendserver.product.domain.option.ProductOption;
import com.commerce.backendserver.product.domain.option.ProductSelectionOption;
import com.commerce.backendserver.product.domain.option.constants.ProductColor;
import com.commerce.backendserver.product.domain.option.constants.ProductStatus;

import java.util.List;

public record ProductOptionResponse(
        Long optionId,
        ProductColor color,
        ProductSelectionOption selectionOption,
        Integer inventory,
        ProductStatus productStatus
) {
    public static ProductOptionResponse convertToOptionResponse(ProductOption productOption) {
        return new ProductOptionResponse(
                productOption.getId(),
                productOption.getColor(),
                productOption.getSelectionOption(),
                productOption.getInventory(),
                productOption.getProductStatus()
        );
    }

    public static List<ProductOptionResponse> convertToOptionResponseList(
            List<ProductOption> productOptions
    ) {
        return productOptions.stream()
                .map(ProductOptionResponse::convertToOptionResponse)
                .toList();
    }
}
