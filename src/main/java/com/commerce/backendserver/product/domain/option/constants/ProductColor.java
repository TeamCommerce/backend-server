package com.commerce.backendserver.product.domain.option.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductColor {

    MATT_BLACK("#000000", "매트 블랙"),
    PURE_WHITE("#FFFFFF", "퓨어 화이트");

    private final String colorCode;
    private final String colorName;
}
