package com.commerce.backendserver.product.fixture;

import static com.commerce.backendserver.product.domain.option.constants.ProductSize.*;
import static com.commerce.backendserver.product.domain.option.constants.ProductStatus.*;

import com.commerce.backendserver.product.domain.option.ProductOption;
import com.commerce.backendserver.product.domain.option.ProductSelectionOption;
import com.commerce.backendserver.product.domain.option.constants.ProductColor;
import com.commerce.backendserver.product.domain.option.constants.ProductSize;
import com.commerce.backendserver.product.domain.option.constants.ProductStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

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
	),
	VALID_OPTION_2(
		"#FFEEE",
		"파랑",
		"blue",
		L,
		"기모",
		"O",
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
			ProductSelectionOption.of(
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
