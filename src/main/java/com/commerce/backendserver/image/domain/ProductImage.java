package com.commerce.backendserver.image.domain;

import com.commerce.backendserver.image.domain.constants.ProductImageCategory;
import com.commerce.backendserver.product.domain.Product;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.STRING;
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

    @Enumerated(STRING)
    @Column(name = "product_image_category")
    private ProductImageCategory imageCategory;

    //== Constructor Method ==//
    @Builder
    private ProductImage(
            final String url,
            final ProductImageCategory imageCategory
    ) {
        super(url);
        this.imageCategory = imageCategory;
    }

    //== Static Factory Method ==//
    public static ProductImage of(
            final String url,
            final ProductImageCategory imageCategory
    ) {
        return ProductImage.builder()
                .url(url)
                .imageCategory(imageCategory)
                .build();
    }

    public void updateProduct(Product product) {
        this.product = product;
    }


}
