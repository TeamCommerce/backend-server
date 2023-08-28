package com.commerce.backendserver.product.domain;

import com.commerce.backendserver.global.auditing.BaseEntity;
import com.commerce.backendserver.image.domain.Image;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Table(name = "t_product_image")
@NoArgsConstructor(access = PROTECTED)
public class ProductImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "product_image_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Embedded
    @AttributeOverride(name = "url", column = @Column(name = "product_image_url", columnDefinition = "longtext"))
    private Image image;

    //== Constructor Method ==//
    @Builder
    private ProductImage(
            Product product,
            Image image
    ) {
        this.product = product;
        this.image = image;
    }

    //== Static Factory Method ==//
    public ProductImage of(
            Product product,
            Image image
    ) {
        return ProductImage.builder()
                .product(product)
                .image(image)
                .build();
    }
}
