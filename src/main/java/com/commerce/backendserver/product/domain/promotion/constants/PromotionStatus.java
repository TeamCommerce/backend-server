package com.commerce.backendserver.product.domain.promotion.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PromotionStatus {

    TO_SCHEDULED("진행 예정 프로모션"),
    AVAILABLE("정상 사용 가능 프로모션"),
    EXPIRED("이미 마감된 프로모션"),
    DISABLED("비활성화 프로모션");

    private final String status;
}
