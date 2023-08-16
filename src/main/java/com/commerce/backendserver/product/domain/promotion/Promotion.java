package com.commerce.backendserver.product.domain.promotion;

import com.commerce.backendserver.product.domain.promotion.constants.PromotionType;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "t_promotion")
public class Promotion {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "promotion_id")
    private Long id;

    @Enumerated(value = EnumType.STRING)
    @Column(columnDefinition = "varchar(20)")
    private PromotionType discountType;

    @Column(columnDefinition = "int unsigned")
    private Integer discountedAmount;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd a HH:mm")
    private LocalDateTime startDateTime;

    @Nullable
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd a HH:mm")
    private LocalDateTime expiredDateTime;
}
