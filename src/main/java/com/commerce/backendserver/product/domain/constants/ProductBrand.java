package com.commerce.backendserver.product.domain.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductBrand {

    ADADIS("Adadis"),
    IUP_STUDIO("IUP Studio");

    private final String brand;
}
