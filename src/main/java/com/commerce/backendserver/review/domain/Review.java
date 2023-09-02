package com.commerce.backendserver.review.domain;

import com.commerce.backendserver.global.auditing.BaseEntity;
import com.commerce.backendserver.review.domain.additionalinfo.AdditionalInfoList;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Table(name = "t_review")
@NoArgsConstructor(access = PROTECTED)
public class Review extends BaseEntity {

    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String contents;

    @Column(nullable = false)
    private Integer score;

    @Embedded
    private AdditionalInfoList additionalInfoList;

    @JoinColumn
    private Long writerId;
}
