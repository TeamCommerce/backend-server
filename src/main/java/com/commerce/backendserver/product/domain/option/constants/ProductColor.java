package com.commerce.backendserver.product.domain.option.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductColor {

    MATT_BLACK("#000000", "매트 블랙", "Matt Black"),
    PURE_WHITE("#FFFFFF", "퓨어 화이트", "Pure White");

    private final String colorCode;
    private final String korColorName;
    private final String engColorName;
}
