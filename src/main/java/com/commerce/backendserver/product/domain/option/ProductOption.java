package com.commerce.backendserver.product.domain.option;

import com.commerce.backendserver.global.auditing.BaseEntity;
import com.commerce.backendserver.product.domain.Product;
import com.commerce.backendserver.product.domain.option.constants.ProductColor;
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

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Embedded
    private ProductColor color;

    @Embedded
    private ProductSelectionOption selectionOption;

    @Column(nullable = false)
    private Integer inventory;

    @Enumerated(value = STRING)
    private ProductStatus status;

    //== Constructor Method ==//
    @Builder
    private ProductOption(
            ProductColor color,
            ProductSelectionOption selectionOption,
            Integer inventory,
            ProductStatus status
    ) {
        this.color = color;
        this.selectionOption = selectionOption;
        this.inventory = inventory;
        this.status = status;
    }

    //== Static Factory Method ==//
    public static ProductOption of(
            final ProductColor color,
            final ProductSelectionOption selectionOption,
            final Integer inventory,
            final ProductStatus status
    ) {
        return ProductOption.builder()
                .color(color)
                .selectionOption(selectionOption)
                .inventory(inventory)
                .status(status)
                .build();
    }

    public void updateProduct(Product product) {
        this.product = product;
    }
}
