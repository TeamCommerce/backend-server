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
            name = "type",
            columnDefinition = "varchar(30)")
    private String type;

    @Column(
            nullable = false,
            name = "val",
            columnDefinition = "varchar(30)")
    private String val;

    //== Constructor Method ==//
    @Builder
    private SelectionOption(
            final String type,
            final String value
    ) {
        this.type = type;
        this.value = value;
    }

    //== Static Factory Method ==//
    public static SelectionOption of(
            final String type,
            final String value
    ) {
        return SelectionOption.builder()
                .type(type)
                .value(value)
                .build();
    }
}
