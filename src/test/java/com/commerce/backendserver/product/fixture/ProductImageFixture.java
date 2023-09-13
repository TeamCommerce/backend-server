package com.commerce.backendserver.product.fixture;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
public enum ProductImageFixture {
    VALID_URL("https://github.com/TeamCommerce/backend-server/assets/112257466/9a9ac240-8a85-41db-9591-103194fb2da4");

    private final String url;

    public List<String> toEntityList() {
        List<String> list = new ArrayList<>();
        list.add(url);
        return list;
    }
}
