package com.commerce.backendserver.product.domain.infra.query;

import com.commerce.backendserver.product.domain.QProduct;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductQueryDslRepositoryImpl implements ProductQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    private final QProduct product = new QProduct("pd");
}
