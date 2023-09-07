package com.commerce.backendserver.product.application.dto;

import com.commerce.backendserver.product.domain.option.ProductOption;
import com.commerce.backendserver.product.domain.option.constants.ProductSize;
import com.commerce.backendserver.product.domain.option.constants.ProductStatus;

import java.util.List;

public record ProductOptionResponse(
        String colorCode,
        String korColorName,
        String engColorName,
        ProductSize size,
        String optionType,
        String optionValue,
        Integer additionalFee,
        Integer inventory,
        ProductStatus productStatus
) {
    public static ProductOptionResponse convertToOptionResponse(ProductOption productOption) {
        return new ProductOptionResponse(
                productOption.getColor().getColorCode(),
                productOption.getColor().getKorColorName(),
                productOption.getColor().getEngColorName(),
                productOption.getSize(),
                productOption.getOption().getOptionType(),
                productOption.getOption().getOptionValue(),
                productOption.getAdditionalFee(),
                productOption.getInventory(),
                productOption.getProductStatus()
        );
    }

    public static List<ProductOptionResponse> convertToOptionResponseList(List<ProductOption> productOptions) {
        return productOptions.stream()
                .map(ProductOptionResponse::convertToOptionResponse)
                .toList();
    }
}
