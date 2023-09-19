package com.commerce.backendserver.product.fixture;

import com.commerce.backendserver.product.domain.option.ProductAdditionalOption;
import com.commerce.backendserver.product.domain.option.ProductColor;
import com.commerce.backendserver.product.domain.option.ProductOption;
import com.commerce.backendserver.product.domain.option.constants.ProductSize;
import com.commerce.backendserver.product.domain.option.constants.ProductStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static com.commerce.backendserver.product.domain.option.constants.ProductSize.TWO_XL;
import static com.commerce.backendserver.product.domain.option.constants.ProductStatus.AVAILABLE;

@Getter
@RequiredArgsConstructor
public enum ProductOptionFixture {

    VALID_OPTION(
            "#FFFFFF",
            "Iup 매트 블랙",
            "Iup Matt Black",
            TWO_XL,
            "쭈리",
            "O",
            2000,
            300,
            AVAILABLE
    );

    private final String colorCode;
    private final String korColorName;
    private final String engColorName;
    private final ProductSize size;
    private final String key;
    private final String value;
    private final Integer additionalFee;
    private final Integer inventory;
    private final ProductStatus status;

    public List<ProductOption> toOptionList() {
        ProductOption entity = ProductOption.of(
                ProductColor.of(
                        colorCode,
                        korColorName,
                        engColorName
                ),
                ProductAdditionalOption.of(
                        key,
                        value,
                        additionalFee
                ),
                inventory,
                status,
                size
        );
        ArrayList<ProductOption> productOptions = new ArrayList<>();
        productOptions.add(entity);
        return productOptions;
    }
}
