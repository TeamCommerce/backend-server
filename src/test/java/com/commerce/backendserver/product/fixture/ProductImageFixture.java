package com.commerce.backendserver.product.fixture;

import com.commerce.backendserver.image.domain.ProductImage;
import com.commerce.backendserver.image.domain.constants.ProductImageCategory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static com.commerce.backendserver.image.domain.constants.ProductImageCategory.MAIN;

@Getter
@RequiredArgsConstructor
public enum ProductImageFixture {
    VALID_IMAGE("https://github.com/TeamCommerce/backend-server/assets/112257466/9a9ac240-8a85-41db-9591-103194fb2da4", MAIN);

    private final String url;
    private final ProductImageCategory category;

    public List<ProductImage> toEntityList() {
        List<ProductImage> list = new ArrayList<>();
        list.add(ProductImage.of(url, category));
        return list;
    }
}
