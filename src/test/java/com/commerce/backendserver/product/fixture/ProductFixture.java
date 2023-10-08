package com.commerce.backendserver.product.fixture;

import static com.commerce.backendserver.product.domain.constants.ProductBrand.*;
import static com.commerce.backendserver.product.domain.constants.ProductCategory.*;
import static com.commerce.backendserver.product.fixture.ProductImageFixture.*;
import static com.commerce.backendserver.product.fixture.ProductOptionFixture.*;
import static org.apache.commons.lang3.RandomStringUtils.*;

import java.util.List;

import com.commerce.backendserver.product.domain.Product;
import com.commerce.backendserver.product.domain.ProductCommonInfo;
import com.commerce.backendserver.product.domain.ProductPriceAttribute;
import com.commerce.backendserver.product.domain.constants.ProductBrand;
import com.commerce.backendserver.product.domain.constants.ProductCategory;
import com.commerce.backendserver.product.domain.promotion.Promotion;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductFixture {

	VALID_PRODUCT(
		IUP_STUDIO,
		"아이없 스튜디오 반팔 상의",
		TOP,
		"Iup Studio 2024 S/S Special Edition",
		30000,
		List.of(VALID_IMAGE),
		List.of(VALID_OPTION)
	),

	VALID_PRODUCT_2(
		IUP_STUDIO,
		"nameB",
		BOT,
		"descriptionB",
		20000,
		List.of(VALID_IMAGE),
		List.of(VALID_OPTION_2)
	),

	VALID_PRODUCT_3(
		IUP_STUDIO,
		"nameC",
		BOT,
		"descriptionX",
		40000,
		List.of(VALID_IMAGE),
		List.of(VALID_OPTION_3)
	),

	NULL_DESCRIPTION(
		IUP_STUDIO,
		"상품 설명 null",
		TOP,
		null,
		30000,
		List.of(VALID_IMAGE),
		List.of(VALID_OPTION)
	),

	TOO_LONG_DESCRIPTION(
		IUP_STUDIO,
		"아이없 스튜디오 반팔 상의",
		TOP,
		randomAlphanumeric(301),
		30000,
		List.of(VALID_IMAGE),
		List.of(VALID_OPTION)
	);

	private final ProductBrand brand;
	private final String name;
	private final ProductCategory category;
	private final String description;
	private final Integer originPrice;
	private final List<ProductImageFixture> images;
	private final List<ProductOptionFixture> options;

	public Product toEntity(
		Promotion promotion
	) {
		return Product.createProduct(
			images.stream().map(ProductImageFixture::toEntity).toList(),
			options.stream().map(ProductOptionFixture::toEntity).toList(),
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
