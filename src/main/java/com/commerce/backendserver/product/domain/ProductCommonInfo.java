package com.commerce.backendserver.product.domain;

import com.commerce.backendserver.global.exception.CommerceException;
import com.commerce.backendserver.product.domain.constants.ProductBrand;
import com.commerce.backendserver.product.domain.constants.ProductCategory;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.commerce.backendserver.product.exception.ProductError.*;
import static jakarta.persistence.EnumType.STRING;
import static java.util.Objects.isNull;
import static lombok.AccessLevel.PROTECTED;
import static org.springframework.util.StringUtils.hasText;

@Getter
@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class ProductCommonInfo {

    private static final int DESCRIPTION_MAX_LENGTH = 300;

    @Enumerated(value = STRING)
    @Column(columnDefinition = "varchar(100)")
    private ProductBrand brand;

    @Column(columnDefinition = "varchar(100)")
    private String name;

    @Enumerated(value = STRING)
    private ProductCategory category;

    @Column(columnDefinition = "varchar(100)")
    private String description;

    //== Constructor Method ==//
    @Builder
    private ProductCommonInfo(
            final ProductBrand brand,
            final String name,
            final ProductCategory category,
            final String description
    ) {
        validateBrand(brand);
        validateName(name);
        validateCategory(category);
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
    private void validateBrand(final ProductBrand brand) {
        if (isNull(brand)) {
            throw CommerceException.of(INVALID_BRAND);
        }
    }

    private void validateName(final String name) {
        if (hasText(name)) {
            throw CommerceException.of(INVALID_PRODUCT_NAME);
        }
    }

    private void validateCategory(final ProductCategory category) {
        if (isNull(category)) {
            throw CommerceException.of(INVALID_PRODUCT_CATEGORY);
        }
    }

    private void validateDescription(final String description) {
        if (isNull(description)) {
            throw CommerceException.of(INVALID_PRODUCT_DESCRIPTION);
        } else if (description.length() > DESCRIPTION_MAX_LENGTH) {
            throw CommerceException.of(TOO_LONG_PRODUCT_DESCRIPTION);
        }
    }
}
