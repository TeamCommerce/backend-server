package com.commerce.backendserver.product.presentation;

import com.commerce.backendserver.product.application.ProductFindService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductSearchController {

    private final ProductFindService productFindService;

//    @GetMapping(path = "{productId}")
//    public ResponseEntity<Product> findProduct(
//            @PathVariable Long productId
//    ) {
//
//    }
}
