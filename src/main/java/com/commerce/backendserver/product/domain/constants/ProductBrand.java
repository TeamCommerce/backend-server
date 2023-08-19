package com.commerce.backendserver.product.domain.constants;

import com.commerce.backendserver.global.exception.CommerceException;
import com.commerce.backendserver.product.exception.ProductError;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

import static com.commerce.backendserver.product.exception.ProductError.UNKNOWN_BRAND;

@Getter
@RequiredArgsConstructor
public enum ProductBrand {

    ADADIS("Adadis"),
    IUP_STUDIO("IUP Studio");

    private final String value;

    public static ProductBrand fromString(String input) {
        Optional<ProductBrand> brand = Arrays.stream(values())
                .filter(b -> b.getValue().equalsIgnoreCase(input))
                .findFirst();

        if (brand.isEmpty()) {
            throw CommerceException.of(UNKNOWN_BRAND);
        }
        return brand.get();
    }
}
