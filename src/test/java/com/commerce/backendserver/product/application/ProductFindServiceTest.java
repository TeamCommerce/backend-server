package com.commerce.backendserver.product.application;

import com.commerce.backendserver.product.domain.Product;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

@Slf4j
@SpringBootTest
class ProductFindServiceTest {

    @Autowired
    private ProductFindService service;

    @Test
    @WithMockUser
    void test() throws Exception {
        //given
        Product singleProductInfoById = service.findSingleProductInfoById(1L);

        //when
        log.warn("{}", singleProductInfoById.getId());

        //then


    }
}
