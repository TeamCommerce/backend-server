package com.commerce.backendserver.product.domain.constants;

import lombok.*;

import static lombok.AccessLevel.PROTECTED;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public enum ProductColor {

    MATT_BLACK("매트 블랙", "#000000"),
    PURE_WHITE("퓨어 화이트", "#ffffff");

    private String colorName;
    private String colorCode;
}
