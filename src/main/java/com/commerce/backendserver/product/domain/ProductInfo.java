package com.commerce.backendserver.product.domain;

import com.commerce.backendserver.product.domain.constants.Brand;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.STRING;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class ProductInfo {

    @Enumerated(value = STRING)
    @Column(columnDefinition = "varchar(100)")
    private Brand brand;

    @Column(columnDefinition = "varchar(100)")
    private String name;

    @Column(columnDefinition = "varchar(100)")
    private String description;

}
