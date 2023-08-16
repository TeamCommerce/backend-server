package com.commerce.backendserver.product.domain;

import com.commerce.backendserver.global.auditing.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private ProductInfo info;

    @Embedded
    private ProductAttribute attribute;

    @Embedded
    private ProductPriceAttribute priceAttribute;
}
