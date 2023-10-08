package com.commerce.backendserver.product.application.dto.response;

import java.util.List;

import com.commerce.backendserver.product.domain.constants.ProductBrand;
import com.commerce.backendserver.product.domain.promotion.constants.PromotionType;

public record ProductDetailResponse(
	Long id,
	String name,
	ProductBrand brand,
	String description,
	Integer originPrice,
	PromotionType promotionType,
	Integer promotionValue,
	Integer finalPrice,
	List<String> colors,
	String mainImage,
	List<String> specificImages,
	List<ProductOptionResponse> options
) {
}
