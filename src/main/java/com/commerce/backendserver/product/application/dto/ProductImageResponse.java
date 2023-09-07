package com.commerce.backendserver.product.application.dto;

import com.commerce.backendserver.image.domain.ProductImage;

import java.util.List;

public record ProductImageResponse(
        List<String> urls
) {
    public static String convertToImageResponse(ProductImage image) {
        return String.valueOf(image.getUrl());
    }

    public static List<String> convertToImageResponseList(List<ProductImage> images) {
        return images.stream()
                .map(ProductImageResponse::convertToImageResponse)
                .toList();
    }
}
