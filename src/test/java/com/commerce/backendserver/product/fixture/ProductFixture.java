package com.commerce.backendserver.product.fixture;

import com.commerce.backendserver.product.domain.Product;
import com.commerce.backendserver.product.domain.ProductCommonInfo;
import com.commerce.backendserver.product.domain.ProductPriceAttribute;
import com.commerce.backendserver.product.domain.constants.ProductBrand;
import com.commerce.backendserver.product.domain.constants.ProductCategory;
import com.commerce.backendserver.product.domain.promotion.Promotion;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.commerce.backendserver.product.domain.constants.ProductBrand.IUP_STUDIO;
import static com.commerce.backendserver.product.domain.constants.ProductCategory.TOP;
import static com.commerce.backendserver.product.fixture.ProductImageFixture.VALID_URL;
import static com.commerce.backendserver.product.fixture.ProductOptionFixture.VALID_OPTION;

@Getter
@RequiredArgsConstructor
public enum ProductFixture {

    VALID_PRODUCT(
            IUP_STUDIO,
            "아이없 스튜디오 반팔 상의",
            TOP,
            "Iup Studio 2024 S/S Special Edition",
            30000,
            VALID_URL.toEntityList()
    );

    private final ProductBrand brand;
    private final String name;
    private final ProductCategory category;
    private final String description;
    private final Integer originPrice;
    private final List<String> images;

    public Product toEntity(
            Promotion promotion
    ) {
        return Product.createProduct(
                images,
                VALID_OPTION.toOptionList(),
                ProductCommonInfo.of(
                        brand,
                        name,
                        category,
                        description
                ),
                ProductPriceAttribute.of(
                        promotion,
                        originPrice
                )
        );
    }
}
