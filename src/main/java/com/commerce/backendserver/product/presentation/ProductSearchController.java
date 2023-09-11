package com.commerce.backendserver.product.presentation;

import com.commerce.backendserver.product.application.ProductFindService;
import com.commerce.backendserver.product.application.dto.ProductSingleSimpleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@Transactional(readOnly = true)
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductSearchController {

    private final ProductFindService productFindService;

    @GetMapping
    public ResponseEntity<ProductSingleSimpleResponse> findSingleProduct(
            @RequestParam Long id
    ) {
        return ResponseEntity.ok(productFindService.toSingleSimpleResponse(id));
    }
}
