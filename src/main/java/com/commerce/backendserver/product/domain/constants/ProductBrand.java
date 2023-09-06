package com.commerce.backendserver.product.domain.constants;

import com.commerce.backendserver.global.exception.CommerceException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

import static com.commerce.backendserver.product.exception.ProductError.UNKNOWN_BRAND;

@Getter
@RequiredArgsConstructor
public enum ProductBrand {

    ADADIS("Adadis"),
    IUP_STUDIO("IUP Studio"),
    BE("Back-End"),
    FE("Front-End");

    private final String value;

    public static ProductBrand fromString(String input) {
        return Arrays.stream(values())
                .filter(b -> b.getValue().equalsIgnoreCase(input))
                .findFirst()
                .orElseThrow(() -> CommerceException.of(UNKNOWN_BRAND));
    }
}
