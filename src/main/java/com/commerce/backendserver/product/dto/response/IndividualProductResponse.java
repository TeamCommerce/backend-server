package com.commerce.backendserver.product.dto.response;

import com.commerce.backendserver.product.domain.Product;
import com.commerce.backendserver.product.domain.constants.ProductBrand;
import com.commerce.backendserver.product.domain.constants.ProductCategory;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class IndividualProductResponse {

    private Product Product;
}
