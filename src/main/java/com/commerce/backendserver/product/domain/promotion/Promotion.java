package com.commerce.backendserver.product.domain.promotion;

import com.commerce.backendserver.global.auditing.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "t_promotion")
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
    private PromotionDiscountAttribute priceAttribute;

    @Embedded
    private PromotionPeriodAttribute periodAttribute;
}
