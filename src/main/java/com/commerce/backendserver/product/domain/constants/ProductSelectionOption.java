package com.commerce.backendserver.product.domain.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public enum ProductSelectionOption {

    FLEECE_TRUE("기모_O"),
    FLEECE_FALSE("기모_X"),
    ELASTIC_BAND_TRUE("쭈리_O"),
    ELASTIC_BAND_FALSE("쭈리_X");

    private String name;
}
