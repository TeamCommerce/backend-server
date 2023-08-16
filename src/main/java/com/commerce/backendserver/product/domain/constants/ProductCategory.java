package com.commerce.backendserver.product.domain.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public enum ProductCategory {

    TOP("상의");

    private String value;
}
