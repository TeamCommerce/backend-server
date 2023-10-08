package com.commerce.backendserver.common.base;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.commerce.backendserver.common.utils.DatabaseCleaner;

public class DatabaseCleanerExtension implements BeforeEachCallback {

	@Override
	public void beforeEach(final ExtensionContext context) {
		DatabaseCleaner databaseCleaner = SpringExtension
			.getApplicationContext(context)
			.getBean(DatabaseCleaner.class);

		databaseCleaner.clear();
	}
}
