package com.commerce.backendserver.product.domain;

import com.commerce.backendserver.product.domain.constants.ProductStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.STRING;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class ProductAttribute {

    @NotNull
    @Column(columnDefinition = "int unsigned")
    private Integer originPrice;

    @NotNull
    @Column(columnDefinition = "int unsigned")
    private Integer inventory;

    @Enumerated(value = STRING)
    private ProductStatus productStatus;
}
