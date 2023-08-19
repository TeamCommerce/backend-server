package com.commerce.backendserver.product.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ProductCreateRequest(
        @NotNull String brand,
        @NotNull String name,
        @NotNull String category,
        @NotNull String description,
        @Nullable Long promotionId,
        @NotNull Integer originPrice,
        @NotNull List<String> colors,
        @NotNull List<String> Sizes
) {
    // todo 개발 예정
}
