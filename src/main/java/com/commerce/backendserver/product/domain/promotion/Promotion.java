package com.commerce.backendserver.product.domain.promotion;

import com.commerce.backendserver.global.auditing.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Table(name = "t_promotion")
@NoArgsConstructor(access = PROTECTED)
public class Promotion extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "promotion_id")
    private Long id;

    @Column(
            name = "name",
            nullable = false,
            columnDefinition = "varchar(200)")
    private String name;

    @Embedded
    private PromotionDiscountAttribute discountAttribute;


    //== Constructor Method ==//
    @Builder(access = PRIVATE)
    private Promotion(
            final String name,
            final PromotionDiscountAttribute discountAttribute
    ) {
        this.name = name;
        this.discountAttribute = discountAttribute;
    }

    //== Static Factory Method ==//
    public static Promotion of(
            final String name,
            final PromotionDiscountAttribute discountAttribute
    ) {
        return Promotion.builder()
                .name(name)
                .discountAttribute(discountAttribute)
                .build();
    }
}
