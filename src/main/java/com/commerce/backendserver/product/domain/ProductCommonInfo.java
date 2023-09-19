package com.commerce.backendserver.product.domain;

import com.commerce.backendserver.global.exception.CommerceException;
import com.commerce.backendserver.product.domain.constants.BestProduct;
import com.commerce.backendserver.product.domain.constants.ProductBrand;
import com.commerce.backendserver.product.domain.constants.ProductCategory;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.commerce.backendserver.product.exception.ProductError.INVALID_PRODUCT_DESCRIPTION;
import static com.commerce.backendserver.product.exception.ProductError.TOO_LONG_PRODUCT_DESCRIPTION;
import static jakarta.persistence.EnumType.STRING;
import static java.util.Objects.isNull;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class ProductCommonInfo {

    private static final int DESCRIPTION_MAX_LENGTH = 300;

    @Enumerated(value = STRING)
    @Column(nullable = false)
    private ProductBrand brand;

    @Column(
            nullable = false,
            columnDefinition = "varchar(100)")
    private String name;

    @Enumerated(value = STRING)
    @Column(nullable = false)
    private ProductCategory category;

    @Column(columnDefinition = "varchar(300)")
    private String description;

    @Column(name = "best", columnDefinition = "varchar(1) default 'F'")
    @Enumerated(EnumType.STRING)
    private BestProduct best = BestProduct.F;

    //== Constructor Method ==//
    @Builder(access = PRIVATE)
    private ProductCommonInfo(
            final ProductBrand brand,
            final String name,
            final ProductCategory category,
            final String description
    ) {
        validateDescription(description);
        this.brand = brand;
        this.name = name;
        this.category = category;
        this.description = description;
    }

    //== Static Factory Method ==//
    public static ProductCommonInfo of(
            final ProductBrand brand,
            final String name,
            final ProductCategory category,
            final String description
    ) {
        return ProductCommonInfo.builder()
                .brand(brand)
                .name(name)
                .category(category)
                .description(description)
                .build();
    }

    //== Validation Method ==//
    private void validateDescription(final String description) {
        if (isNull(description)) {
            throw CommerceException.of(INVALID_PRODUCT_DESCRIPTION);
        } else if (description.length() > DESCRIPTION_MAX_LENGTH) {
            throw CommerceException.of(TOO_LONG_PRODUCT_DESCRIPTION);
        }
    }
}
