package com.commerce.backendserver.product.domain;

import com.commerce.backendserver.global.auditing.BaseEntity;
import com.commerce.backendserver.image.domain.ProductImage;
import com.commerce.backendserver.product.domain.option.ProductOption;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.GenerationType.IDENTITY;
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

    @OneToMany(
            mappedBy = "product",
            cascade = PERSIST,
            orphanRemoval = true)
    private List<ProductImage> images = new ArrayList<>();

    @OneToMany(
            mappedBy = "product",
            cascade = PERSIST,
            orphanRemoval = true)
    private List<ProductOption> options = new ArrayList<>();


    //== Constructor Method ==//
    @Builder
    private Product(
            final ProductCommonInfo info,
            final ProductPriceAttribute priceAttribute
    ) {
        this.info = info;
        this.priceAttribute = priceAttribute;
    }

    //== Static Factory Method ==//
    public static Product toProduct(
            final ProductCommonInfo info,
            final ProductPriceAttribute priceAttribute
    ) {
        return Product.builder()
                .info(info)
                .priceAttribute(priceAttribute)
                .build();
    }
}
