package com.commerce.backendserver.product.domain.option.constants;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class ProductColor {

    private String colorCode;

    private String korColorName;

    private String engColorName;
}
