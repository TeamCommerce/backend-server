package com.commerce.backendserver.common.base;

import com.commerce.backendserver.global.config.AuditingConfig;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@DataJpaTest(
        includeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = AuditingConfig.class
        ))
public abstract class RepositoryTestBase {
}
