package com.commerce.backendserver.product.presentation;

import com.commerce.backendserver.global.dto.response.ResponseWrapper;
import com.commerce.backendserver.product.application.ProductFindService;
import com.commerce.backendserver.product.application.dto.response.ProductDetailResponse;
import com.commerce.backendserver.product.application.dto.response.ProductSimpleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional(readOnly = true)
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductSearchController {

    private final ProductFindService productFindService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailResponse> findSingleProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productFindService.toSingleDetailResponse(id));
    }

    @GetMapping("/best")
    public ResponseWrapper<ProductSimpleResponse> findBestProducts() {
        return productFindService.toBestProductsResponse();
    }
}
