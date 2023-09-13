package com.commerce.backendserver.product.domain.option;

import com.commerce.backendserver.product.domain.option.constants.ProductSize;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.STRING;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class ProductSelectionOption {

    @Enumerated(value = STRING)
    private ProductSize size;

    @Column(
            nullable = false,
            name = "option_type",
            columnDefinition = "varchar(30)")
    private String optionType;

    @Column(
            nullable = false,
            name = "option_value",
            columnDefinition = "varchar(30)")
    private String optionValue;

    private Integer additionalFee;

    //== Constructor Method ==//
    @Builder
    private ProductSelectionOption(
            final ProductSize size,
            final String optionType,
            final String optionValue,
            final Integer additionalFee
    ) {
        this.size = size;
        this.optionType = optionType;
        this.optionValue = optionValue;
        this.additionalFee = additionalFee;
    }

    //== Static Factory Method ==//
    public static ProductSelectionOption of(
            final ProductSize size,
            final String optionType,
            final String optionValue,
            final Integer additionalFee
    ) {
        return ProductSelectionOption.builder()
                .size(size)
                .optionType(optionType)
                .optionValue(optionValue)
                .additionalFee(additionalFee)
                .build();
    }
}
