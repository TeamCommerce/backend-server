package com.commerce.backendserver.product.domain.option;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class SelectionOption {

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

    //== Constructor Method ==//
    @Builder
    private SelectionOption(
            final String optionType,
            final String optionValue
    ) {
        this.optionType = optionType;
        this.optionValue = optionValue;
    }

    //== Static Factory Method ==//
    public static SelectionOption of(
            final String optionType,
            final String optionValue
    ) {
        return SelectionOption.builder()
                .optionType(optionType)
                .optionValue(optionValue)
                .build();
    }
}
