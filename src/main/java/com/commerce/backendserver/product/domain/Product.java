package com.commerce.backendserver.product.domain;

import com.commerce.backendserver.global.auditing.BaseEntity;
import com.commerce.backendserver.global.exception.CommerceException;
import com.commerce.backendserver.product.domain.option.ProductOption;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static com.commerce.backendserver.product.exception.ProductError.INVALID_OPTIONS;
import static com.commerce.backendserver.product.exception.ProductError.INVALID_PRODUCT_INFO;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.GenerationType.IDENTITY;
import static java.util.Objects.isNull;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Table(name = "t_product")
@NoArgsConstructor(access = PROTECTED)
public class Product extends BaseEntity {

    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Embedded
    private ProductCommonInfo info;

    @Embedded
    private ProductPriceAttribute priceAttribute;

    @OneToMany(mappedBy = "product", cascade = PERSIST, orphanRemoval = true)
    private List<ProductOption> options = new ArrayList<>();

    //== Constructor Method ==//
    @Builder
    private Product(
            final ProductCommonInfo info,
            final ProductPriceAttribute priceAttribute,
            final List<ProductOption> options
    ) {
        validateInfo(info);
        validatePriceAttribute(priceAttribute);
        validateOptions(options);
        this.info = info;
        this.priceAttribute = priceAttribute;
        this.options = options;
    }

    //== Static Factory Method ==//
    public static Product Of(
            final ProductCommonInfo info,
            final ProductPriceAttribute priceAttribute,
            final List<ProductOption> options
    ) {
        return Product.builder()
                .info(info)
                .priceAttribute(priceAttribute)
                .options(options)
                .build();
    }

    //== Validation Method ==//
    private void validateInfo(final ProductCommonInfo info) {
        if (isNull(info)) {
            throw CommerceException.of(INVALID_PRODUCT_INFO);
        }
    }

    private void validatePriceAttribute(final ProductPriceAttribute priceAttribute) {
        if (isNull(priceAttribute)) {
            throw CommerceException.of(INVALID_PRODUCT_INFO);
        }
    }

    private void validateOptions(final List<ProductOption> options) {
        if (options.isEmpty()) {
            throw CommerceException.of(INVALID_OPTIONS);
        }
    }
}
