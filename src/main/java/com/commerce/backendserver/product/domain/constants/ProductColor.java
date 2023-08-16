package com.commerce.backendserver.product.domain.constants;

import lombok.*;

import static lombok.AccessLevel.PROTECTED;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public enum ProductColor {

    BLACK("Black", "#000000");

    private String colorName;
    private String colorCode;
}
