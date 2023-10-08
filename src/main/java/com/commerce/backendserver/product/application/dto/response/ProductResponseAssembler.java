package com.commerce.backendserver.product.application.dto.response;

import static lombok.AccessLevel.*;

import java.util.List;

import com.commerce.backendserver.global.dto.response.ResponseWrapper;
import com.commerce.backendserver.product.domain.Product;
import com.commerce.backendserver.product.domain.option.ProductOption;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class ProductResponseAssembler {

	//== Wrapper Method ==//
	public static ResponseWrapper<ProductSimpleResponse> wrapSimpleResponses(List<Product> products) {
		List<ProductSimpleResponse> response = transferToSimpleResponses(products);
		return new ResponseWrapper<>(response);
	}

	//== Entity List To Response List ==//
	private static List<ProductSimpleResponse> transferToSimpleResponses(List<Product> products) {
		return products.stream()
			.map(ProductResponseAssembler::transferToSimpleResponse)
			.toList();
	}

	public static List<ProductOptionResponse> transferToOptionResponses(List<ProductOption> productOptions) {
		return productOptions.stream()
			.map(ProductResponseAssembler::transferToOptionResponse)
			.toList();
	}

	private static ProductSimpleResponse transferToSimpleResponse(Product product) {
		return new ProductSimpleResponse(
			product.getId(),
			product.getCommonInfo().getName(),
			product.getCommonInfo().getBrand(),
			product.getCommonInfo().getDescription(),
			product.getPriceAttribute().getOriginPrice(),
			product.getPriceAttribute().getPromotion().getType(),
			product.getPriceAttribute().getPromotion().getPromotionValue(),
			product.getPriceAttribute().applyPromotionDiscount(),
			product.getDistinctColors(),
			product.getMainImage());
	}

	private static ProductOptionResponse transferToOptionResponse(ProductOption productOption) {
		return new ProductOptionResponse(
			productOption.getId(),
			productOption.getColor(),
			productOption.getAdditionalOption(),
			productOption.getInventory(),
			productOption.getStatus(),
			productOption.getSize()
		);
	}

	public static ProductDetailResponse transferToDetailResponse(Product product) {
		ProductSimpleResponse common = ProductResponseAssembler.transferToSimpleResponse(product);
		return getProductDetailResponse(product, common);
	}

	private static ProductDetailResponse getProductDetailResponse(
		Product product,
		ProductSimpleResponse common
	) {
		return new ProductDetailResponse(
			common.id(),
			common.name(),
			common.brand(),
			common.description(),
			common.originPrice(),
			common.promotionType(),
			common.promotionValue(),
			common.finalPrice(),
			common.colors(),
			common.mainImage(),
			product.getSpecificImage(),
			ProductResponseAssembler.transferToOptionResponses(product.getOptions()));
	}
}
