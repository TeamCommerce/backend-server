package com.commerce.backendserver.review.domain.additionalinfo.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InfoName {
    SIZE("사이즈", 1),
    ;

    private final String value;
    private final int order;
}
