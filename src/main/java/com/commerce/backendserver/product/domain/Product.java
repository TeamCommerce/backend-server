package com.commerce.backendserver.product.domain;

import com.commerce.backendserver.global.auditing.BaseEntity;
import com.commerce.backendserver.product.domain.promotion.Promotion;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
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
    private ProductIntroductionInfo introductionInfo;

    @Column(columnDefinition = "int unsigned")
    private Integer originPrice;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;

    @Embedded
    private ProductOrderingInfo orderingInfo;
}
