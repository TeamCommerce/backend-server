package com.commerce.backendserver.product.domain.option;

import com.commerce.backendserver.global.auditing.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Table(name = "t_selction_option")
@NoArgsConstructor(access = PROTECTED)
public class SelectionOption extends BaseEntity {

    @Id
    @Column(name = "selection_option_id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

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
