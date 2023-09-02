package com.commerce.backendserver.review.domain.additionalinfo;

import com.commerce.backendserver.review.domain.Review;
import com.commerce.backendserver.review.domain.additionalinfo.constants.InfoName;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Table(name = "t_additional_info")
@NoArgsConstructor(access = PROTECTED)
public class AdditionalInfo {

    @Id
    @Column(name = "additional_info_id")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Enumerated(value = STRING)
    @Column(nullable = false)
    private InfoName infoName;

    @Column(nullable = false)
    private String infoValue;

    @JoinColumn(name = "review_id")
    @ManyToOne(fetch = LAZY)
    private Review review;
}
