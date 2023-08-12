package com.commerce.backendserver.product.domain;

import com.commerce.backendserver.product.domain.constants.ProductStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static jakarta.persistence.EnumType.STRING;

@Getter
@Embeddable
@RequiredArgsConstructor
public class ProductOrderingInfo {

    @Column(columnDefinition = "int unsigned")
    private Integer inventory;

    @Column(columnDefinition = "varchar(100)")
    @Enumerated(value = STRING)
    private ProductStatus status;
}
