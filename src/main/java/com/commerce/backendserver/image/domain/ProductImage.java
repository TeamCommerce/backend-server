package com.commerce.backendserver.image.domain;

import com.commerce.backendserver.product.domain.Product;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@DiscriminatorValue("PRODUCT")
@NoArgsConstructor(access = PROTECTED)
public class ProductImage extends Image {

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    //== Constructor Method ==//
    @Builder
    private ProductImage(
            final String url,
            final Product product
    ) {
        super(url);
        this.product = product;
    }

    //== Static Factory Method ==//
    public ProductImage of(
            final String url,
            final Product product
    ) {
        return ProductImage.builder()
                .url(url)
                .product(product)
                .build();
    }
}
