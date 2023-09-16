package com.commerce.backendserver.product.application.dto;

import com.commerce.backendserver.product.domain.option.ProductOption;
import com.commerce.backendserver.product.domain.option.ProductSelectionOption;
import com.commerce.backendserver.product.domain.option.constants.ProductColor;
import com.commerce.backendserver.product.domain.option.constants.ProductSize;
import com.commerce.backendserver.product.domain.option.constants.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ProductOptionResponse {

    private Long optionId;
    private ProductColor color;
    private ProductSelectionOption selectionOption;
    private Integer inventory;
    private ProductStatus status;
    private ProductSize size;

    public static ProductOptionResponse of(
            final Long optionId,
            final ProductColor color,
            final ProductSelectionOption selectionOption,
            final Integer inventory,
            final ProductStatus status,
            final ProductSize size
    ) {
        return ProductOptionResponse.builder()
                .optionId(optionId)
                .color(color)
                .selectionOption(selectionOption)
                .inventory(inventory)
                .status(status)
                .size(size)
                .build();
    }

    private static ProductOptionResponse toSingleResponse(
            final ProductOption productOption
    ) {
        return ProductOptionResponse.of(
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
