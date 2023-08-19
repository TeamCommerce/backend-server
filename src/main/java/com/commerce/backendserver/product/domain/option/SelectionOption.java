package com.commerce.backendserver.product.domain.option;

import com.commerce.backendserver.global.auditing.BaseEntity;
import jakarta.persistence.*;
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

    @Column(name = "type", columnDefinition = "varchar(30)")
    private String type;

    @Column(name = "value", columnDefinition = "varchar(30)")
    private String value;
}
