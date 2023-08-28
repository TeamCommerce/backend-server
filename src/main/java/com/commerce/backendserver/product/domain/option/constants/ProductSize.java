package com.commerce.backendserver.product.domain.option.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductSize {

    TWO_XL("110", "2XL"),
    XL("105", "XL"),
    L("95", "L"),
    M("90", "M"),
    S("85", "S"),
    XS("80", "XS"),
    TWO_XS("75", "2XS");

    private final String sizeValue;
    private final String sizeName;
}
