package com.commerce.backendserver.common.base;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Repository;

import com.commerce.backendserver.common.config.QueryDslConfig;

@Import(QueryDslConfig.class)
@DataJpaTest(
	includeFilters = @ComponentScan.Filter(
		type = FilterType.ANNOTATION,
		classes = Repository.class
	)
)
public abstract class RepositoryTestBase {
}
