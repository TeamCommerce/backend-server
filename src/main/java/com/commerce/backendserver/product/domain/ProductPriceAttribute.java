package com.commerce.backendserver.product.domain;

import com.commerce.backendserver.product.domain.promotion.Promotion;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class ProductPriceAttribute {

    @NotNull
    @Column(columnDefinition = "int unsigned")
    private Integer originPrice;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;
}
