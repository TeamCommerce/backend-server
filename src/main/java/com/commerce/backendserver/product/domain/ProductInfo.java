package com.commerce.backendserver.product.domain;

import com.commerce.backendserver.product.domain.constants.Brand;
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
public class ProductInfo {

    @NotNull
    @Enumerated(value = STRING)
    @Column(columnDefinition = "varchar(100)")
    private Brand brand;

    @NotNull
    @Column(columnDefinition = "varchar(100)")
    private String name;

    @NotNull
    @Column(columnDefinition = "varchar(100)")
    private String description;

}
