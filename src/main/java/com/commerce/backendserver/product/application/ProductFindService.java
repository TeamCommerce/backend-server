package com.commerce.backendserver.product.application;

import static com.commerce.backendserver.product.exception.ProductError.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.commerce.backendserver.global.dto.response.ResponseWrapper;
import com.commerce.backendserver.global.exception.CommerceException;
import com.commerce.backendserver.product.application.dto.response.ProductDetailResponse;
import com.commerce.backendserver.product.application.dto.response.ProductResponseAssembler;
import com.commerce.backendserver.product.application.dto.response.ProductSimpleResponse;
import com.commerce.backendserver.product.domain.Product;
import com.commerce.backendserver.product.domain.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductFindService {

	private final ProductRepository productRepository;

	public ProductDetailResponse toSingleDetailResponse(Long id) {
		Product product = productRepository.findProductInfoById(id)
			.orElseThrow(() -> CommerceException.of(PRODUCT_NOT_FOUND));

		return ProductResponseAssembler.transferToDetailResponse(product);
	}

	public ResponseWrapper<ProductSimpleResponse> toBestProductsResponse() {
		List<Product> bestProducts = productRepository.findBestProducts();
		validateProducts(bestProducts);

		return ProductResponseAssembler.wrapSimpleResponses(bestProducts);
	}

	//== Validation Method ==//
	private void validateProducts(List<Product> products) {
		if (products.isEmpty()) {
			throw CommerceException.of(PRODUCT_NOT_FOUND);
		}
	}
}

