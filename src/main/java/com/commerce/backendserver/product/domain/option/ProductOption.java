package com.commerce.backendserver.product.domain.option;

import com.commerce.backendserver.global.auditing.BaseEntity;
import com.commerce.backendserver.product.domain.Product;
import com.commerce.backendserver.product.domain.option.constants.ProductColor;
import com.commerce.backendserver.product.domain.option.constants.ProductSize;
import com.commerce.backendserver.product.domain.option.constants.ProductStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Table(name = "t_product_option")
@NoArgsConstructor(access = PROTECTED)
public class ProductOption extends BaseEntity {

    @Id
    @Column(name = "product_option_id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    //    @JsonManagedReference
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Enumerated(value = STRING)
    private ProductColor color;

    @Enumerated(value = STRING)
    private ProductSize size;

    @Embedded
    private SelectionOption option;

    private Integer additionalFee;

    @Column(nullable = false)
    private Integer inventory;

    @Enumerated(value = STRING)
    private ProductStatus productStatus;

    //== Constructor Method ==//
    @Builder
    private ProductOption(
            Product product,
            ProductColor color,
            ProductSize size,
            SelectionOption option,
            Integer additionalFee,
            Integer inventory,
            ProductStatus productStatus
    ) {
        this.product = product;
        this.color = color;
        this.size = size;
        this.option = option;
        this.additionalFee = additionalFee;
        this.inventory = inventory;
        this.productStatus = productStatus;
    }

    //== Static Factory Method ==//
    public ProductOption of(
            Product product,
            ProductColor color,
            ProductSize size,
            SelectionOption option,
            Integer additionalFee,
            Integer inventory,
            ProductStatus productStatus
    ) {
        return ProductOption.builder()
                .product(product)
                .color(color)
                .size(size)
                .option(option)
                .additionalFee(additionalFee)
                .inventory(inventory)
                .productStatus(productStatus)
                .build();
    }
}
