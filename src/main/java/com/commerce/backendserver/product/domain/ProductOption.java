package com.commerce.backendserver.product.domain;

import com.commerce.backendserver.product.domain.constants.ProductColor;
import com.commerce.backendserver.product.domain.constants.ProductSize;
import com.commerce.backendserver.product.domain.constants.ProductStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Table(name = "t_product_option")
@NoArgsConstructor(access = PROTECTED)
public class ProductOption {

    @Id
    @Column(name = "promotion_option_id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(cascade = PERSIST)
    @JoinColumn(name = "product_id")
    private Product product;

    private ProductColor color;

    private ProductSize size;

    @Column(columnDefinition = "int unsigned")
    private Integer inventory;

    @Enumerated(value = STRING)
    private ProductStatus productStatus;
}
