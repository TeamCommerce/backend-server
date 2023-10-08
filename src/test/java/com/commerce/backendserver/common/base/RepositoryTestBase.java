package com.commerce.backendserver.common.base;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

import com.commerce.backendserver.global.config.AuditingConfig;
import com.commerce.backendserver.global.config.QueryDslConfig;

@Import(QueryDslConfig.class)
@DataJpaTest(
	includeFilters = @ComponentScan.Filter(
		type = FilterType.ASSIGNABLE_TYPE,
		classes = AuditingConfig.class
	))
public abstract class RepositoryTestBase {
}
