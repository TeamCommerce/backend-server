package com.commerce.backendserver.review.domain.additionalinfo.constants;

import com.commerce.backendserver.global.exception.CommerceException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

import static com.commerce.backendserver.review.exception.ReviewError.NOT_EXIST_INFO_NAME;

@Getter
@RequiredArgsConstructor
public enum InfoName {

    SIZE("사이즈", 1, String.class),
    HEIGHT("키", 4, Integer.class),
    WEIGHT("몸무게", 5, Integer.class),
    ;

    private final String value;
    private final int order;
    private final Class<? extends Serializable> type;

    public static InfoName matchInfoName(String infoName) {
        try {
            return InfoName.valueOf(infoName);
        } catch (IllegalArgumentException e) {
            throw CommerceException.of(NOT_EXIST_INFO_NAME);
        }
    }
}
