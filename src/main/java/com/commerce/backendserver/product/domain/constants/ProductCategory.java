package com.commerce.backendserver.product.domain.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductCategory {

    TOP("상의"),
    BOT("하의");

    private final String value;
}
