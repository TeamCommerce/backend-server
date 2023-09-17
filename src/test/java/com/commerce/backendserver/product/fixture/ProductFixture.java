package com.commerce.backendserver.product.fixture;

import com.commerce.backendserver.image.domain.ProductImage;
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
import static com.commerce.backendserver.product.fixture.ProductImageFixture.VALID_IMAGE;
import static com.commerce.backendserver.product.fixture.ProductOptionFixture.VALID_OPTION;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

@Getter
@RequiredArgsConstructor
public enum ProductFixture {

    VALID_PRODUCT(
            IUP_STUDIO,
            "아이없 스튜디오 반팔 상의",
            TOP,
            "Iup Studio 2024 S/S Special Edition",
            30000,
            VALID_IMAGE.toEntityList()
    ),

    NULL_DESCRIPTION(
            IUP_STUDIO,
            "상품 설명 null",
            TOP,
            null,
            30000,
            VALID_IMAGE.toEntityList()
    ),

    TOO_LONG_DESCRIPTION(
            IUP_STUDIO,
            "아이없 스튜디오 반팔 상의",
            TOP,
            randomAlphanumeric(301),
            30000,
            VALID_IMAGE.toEntityList()
    );

    private final ProductBrand brand;
    private final String name;
    private final ProductCategory category;
    private final String description;
    private final Integer originPrice;
    private final List<ProductImage> images;

    public Product toEntity(
            Promotion promotion
    ) {
        return Product.createProduct(
                VALID_IMAGE.toEntityList(),
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
