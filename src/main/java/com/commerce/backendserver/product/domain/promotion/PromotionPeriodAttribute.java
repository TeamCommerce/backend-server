package com.commerce.backendserver.product.domain.promotion;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class PromotionPeriodAttribute {

    @Column(
            nullable = false,
            name = "start_date_time",
            columnDefinition = "datetime")
    @JsonFormat(
            shape = STRING,
            pattern = "yyyy-MM-dd a HH:mm")
    private LocalDateTime startDateTime;

    @Column(
            name = "end_date_time",
            columnDefinition = "datetime")
    @JsonFormat(
            shape = STRING,
            pattern = "yyyy-MM-dd a HH:mm")
    private LocalDateTime endDateTime;
}
