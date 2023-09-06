package com.commerce.backendserver.product.presentation;

import com.commerce.backendserver.product.application.ProductFindService;
import com.commerce.backendserver.product.application.dto.ProductSingleSimpleResponse;
import com.commerce.backendserver.product.domain.Product;
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

    @GetMapping(path = "/{productId}")
    public ResponseEntity<ProductSingleSimpleResponse> findSingleProduct(
            @PathVariable Long productId
    ) {
        Product foundProduct = productFindService.findSingleProductInfo(productId);
        return ResponseEntity.ok(productFindService.toSingleSimpleResponse(foundProduct));
    }
}
