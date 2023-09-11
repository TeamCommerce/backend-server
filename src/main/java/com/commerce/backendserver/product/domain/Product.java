package com.commerce.backendserver.product.domain;

import com.commerce.backendserver.global.auditing.BaseEntity;
import com.commerce.backendserver.image.domain.ProductImage;
import com.commerce.backendserver.product.domain.option.ProductOption;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.OnDelete;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;
import static org.hibernate.annotations.OnDeleteAction.CASCADE;

@Entity
@Getter
@Slf4j
@Table(name = "t_product")
@NoArgsConstructor(access = PROTECTED)
public class Product extends BaseEntity {

    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @OneToMany(
            mappedBy = "product",
            cascade = PERSIST,
            orphanRemoval = true)
    @OnDelete(action = CASCADE)
    private final List<ProductImage> images = new ArrayList<>();

    @OneToMany(
            mappedBy = "product",
            cascade = PERSIST,
            orphanRemoval = true)
    @OnDelete(action = CASCADE)
    private final List<ProductOption> options = new ArrayList<>();

    @Embedded
    private ProductCommonInfo commonInfo;

    @Embedded
    private ProductPriceAttribute priceAttribute;


    //== Constructor Method ==//
    @Builder(access = PRIVATE)
    private Product(
            final List<String> images,
            final List<ProductOption> options,
            final ProductCommonInfo commonInfo,
            final ProductPriceAttribute priceAttribute
    ) {
        applyImages(images);
        applyOptions(options);
        this.commonInfo = commonInfo;
        this.priceAttribute = priceAttribute;
    }

    //== Static Factory Method ==//
    public static Product createProduct(
            final List<String> images,
            final List<ProductOption> options,
            final ProductCommonInfo commonInfo,
            final ProductPriceAttribute priceAttribute
    ) {
        return Product.builder()
                .images(images)
                .options(options)
                .commonInfo(commonInfo)
                .priceAttribute(priceAttribute)
                .build();
    }

    //== Business Method ==//
    private void applyImages(List<String> imageUrls) {
        this.images.addAll(
                imageUrls.stream()
                        .map(imageUrl -> ProductImage.of(imageUrl, this))
                        .toList()
        );
    }

    private void applyOptions(List<ProductOption> options) {
        options.forEach(option -> option.updateProduct(this));
        this.options.addAll(options);
    }

    //== Utility Method ==//
}
