package com.commerce.backendserver.product.domain.option;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Embeddable
@AllArgsConstructor
@Builder(access = PRIVATE)
@NoArgsConstructor(access = PROTECTED)
public class ProductColor {

    private String colorCode;

    private String korColorName;

    private String engColorName;

    public static ProductColor of(
            final String colorCode,
            final String korColorName,
            final String engColorName
    ) {
        return ProductColor.builder()
                .colorCode(colorCode)
                .korColorName(korColorName)
                .engColorName(engColorName)
                .build();
    }
}
