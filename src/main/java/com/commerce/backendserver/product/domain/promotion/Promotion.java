package com.commerce.backendserver.product.domain.promotion;

import com.commerce.backendserver.global.auditing.BaseEntity;
import com.commerce.backendserver.product.domain.promotion.constants.PromotionType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.STRING;
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

    @Column(
            nullable = false,
            columnDefinition = "varchar(100)")
    @Enumerated(value = STRING)
    private PromotionType type;

    @Column(nullable = false)
    private Integer promotionValue;

    //== Constructor Method ==//
    @Builder(access = PRIVATE)
    private Promotion(
            final String name,
            final PromotionType type,
            final Integer promotionValue
    ) {
        this.name = name;
        this.type = type;
        this.promotionValue = promotionValue;
    }

    //== Static Factory Method ==//
    public static Promotion of(
            final String name,
            final PromotionType type,
            final Integer promotionValue
    ) {
        return Promotion.builder()
                .name(name)
                .type(type)
                .promotionValue(promotionValue)
                .build();
    }
}
