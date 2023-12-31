package com.commerce.backendserver.product.fixture;

import static com.commerce.backendserver.product.domain.option.constants.ProductSize.*;
import static com.commerce.backendserver.product.domain.option.constants.ProductStatus.*;

import com.commerce.backendserver.product.domain.option.ProductAdditionalOption;
import com.commerce.backendserver.product.domain.option.ProductColor;
import com.commerce.backendserver.product.domain.option.ProductOption;
import com.commerce.backendserver.product.domain.option.constants.ProductSize;
import com.commerce.backendserver.product.domain.option.constants.ProductStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductOptionFixture {

	VALID_OPTION(
		"#FFFFFF",
		"Iup_매트_블랙",
		"Iup_Matt_Black",
		TWO_XL,
		"쭈리",
		"O",
		2000,
		300,
		AVAILABLE
	),
	VALID_OPTION_2(
		"#FFEEE",
		"파랑",
		"blue",
		L,
		"기모",
		"x",
		1000,
		100,
		AVAILABLE
	),
	VALID_OPTION_3(
		"#FFEEEE",
		"빨강",
		"red",
		TWO_XS,
		"스판",
		"O",
		2000,
		150,
		AVAILABLE
	),
	;

	private final String colorCode;
	private final String korColorName;
	private final String engColorName;
	private final ProductSize size;
	private final String key;
	private final String value;
	private final Integer additionalFee;
	private final Integer inventory;
	private final ProductStatus status;

	public ProductOption toEntity() {
		return ProductOption.of(
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
	}
}
