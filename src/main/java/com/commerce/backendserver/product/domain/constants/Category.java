package com.commerce.backendserver.product.domain.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {

    SHIRT("상의"),
    PANTS("하의"),
    SET("상의/하의 세트");

    private final String category;
}
