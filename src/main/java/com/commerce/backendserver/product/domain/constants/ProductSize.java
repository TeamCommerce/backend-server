package com.commerce.backendserver.product.domain.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public enum ProductSize {

    X_SMALL(90, "XS");

    private Integer numberSize;
    private String stringSize;
}
