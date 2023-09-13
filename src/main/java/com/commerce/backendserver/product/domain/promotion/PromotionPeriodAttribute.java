package com.commerce.backendserver.product.domain.promotion;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Embeddable
@Builder(access = PRIVATE)
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
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

    public static PromotionPeriodAttribute of(
            final LocalDateTime startDateTime,
            final LocalDateTime endDateTime
    ) {
        return PromotionPeriodAttribute.builder()
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
                .build();
    }
}
