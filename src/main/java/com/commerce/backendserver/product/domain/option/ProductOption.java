package com.commerce.backendserver.product.domain.option;

import com.commerce.backendserver.product.domain.Product;
import com.commerce.backendserver.product.domain.option.constants.ProductColor;
import com.commerce.backendserver.product.domain.option.constants.ProductSize;
import com.commerce.backendserver.product.domain.option.constants.ProductStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;
import static org.hibernate.annotations.OnDeleteAction.CASCADE;

@Getter
@Entity
@Table(name = "t_product_option")
@NoArgsConstructor(access = PROTECTED)
public class ProductOption {

    @Id
    @Column(name = "product_option_id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @OnDelete(action = CASCADE)
    @JoinColumn(name = "product_id")
    private Product product;

    @Enumerated(value = STRING)
    private ProductColor color;

    @Enumerated(value = STRING)
    private ProductSize size;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "option_id")
    private SelectionOption option;

    @Column(columnDefinition = "int unsigned")
    private int additionalFee;

    @Column(columnDefinition = "int unsigned")
    private Integer inventory;

    @Enumerated(value = STRING)
    private ProductStatus productStatus;
}
