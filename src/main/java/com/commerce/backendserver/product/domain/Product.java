package com.commerce.backendserver.product.domain;

import com.commerce.backendserver.global.auditing.BaseEntity;
import com.commerce.backendserver.global.exception.CommerceException;
import com.commerce.backendserver.image.domain.ProductImage;
import com.commerce.backendserver.product.domain.option.ProductOption;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.commerce.backendserver.image.domain.constants.ProductImageCategory.MAIN;
import static com.commerce.backendserver.image.domain.constants.ProductImageCategory.SPECIFIC;
import static com.commerce.backendserver.product.exception.ProductError.INVALID_PRODUCT_MAIN_IMAGE;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;
import static org.hibernate.annotations.OnDeleteAction.CASCADE;

@Entity
@Getter
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
    private List<ProductImage> images = new ArrayList<>();

    @OneToMany(
            mappedBy = "product",
            cascade = PERSIST,
            orphanRemoval = true)
    @OnDelete(action = CASCADE)
    private List<ProductOption> options = new ArrayList<>();

    @Embedded
    private ProductCommonInfo commonInfo;

    @Embedded
    private ProductPriceAttribute priceAttribute;


    //== Constructor Method ==//
    @Builder(access = PRIVATE)
    private Product(
            final List<ProductImage> images,
            final List<ProductOption> options,
            final ProductCommonInfo commonInfo,
            final ProductPriceAttribute priceAttribute
    ) {
        applyImages(images);
        applyOptions(options);
        this.images = images;
        this.options = options;
        this.commonInfo = commonInfo;
        this.priceAttribute = priceAttribute;
    }

    //== Static Factory Method ==//
    public static Product createProduct(
            final List<ProductImage> images,
            final List<ProductOption> options,
            final ProductCommonInfo commonInfo,
            final ProductPriceAttribute priceAttribute
    ) {
        return new Product(
                images,
                options,
                commonInfo,
                priceAttribute
        );
    }

    //== Business Method ==//
    private void applyImages(
            List<ProductImage> images
    ) {
        images.forEach(image -> image.updateProduct(this));
        this.images.addAll(images);
    }

    private void applyOptions(
            List<ProductOption> options
    ) {
        options.forEach(option -> option.updateProduct(this));
        this.options.addAll(options);
    }

    public List<String> getDistinctColors() {
        return this.options.stream()
                .map(option -> option.getColor().getColorCode())
                .distinct() // 중복 제거
                .collect(Collectors.toList());
    }

    public String getMainImage() {
        return this.getImages().stream()
                .filter(image -> image.getImageCategory().equals(MAIN))
                .map(ProductImage::getUrl)
                .findFirst()
                .orElseThrow(() -> CommerceException.of(INVALID_PRODUCT_MAIN_IMAGE));
    }

    public List<String> getSpecificImage() {
        return this.getImages().stream()
                .filter(image -> image.getImageCategory().equals(SPECIFIC))
                .map(ProductImage::getUrl)
                .toList();
    }
}
