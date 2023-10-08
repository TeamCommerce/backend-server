package com.commerce.backendserver.common.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.test.context.TestComponent;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@TestComponent
public class DatabaseCleaner {

	@PersistenceContext
	private EntityManager entityManager;

	private final List<String> tableNames = new ArrayList<>();

	@PostConstruct
	@SuppressWarnings("unchecked")
	private void initTableNames() {
		List<Object[]> tableInfoList = entityManager.createNativeQuery("SHOW TABLES").getResultList();
		tableInfoList.forEach(tableInfo -> {
			String tableName = (String)tableInfo[0];
			tableNames.add(tableName);
		});
	}

	@Transactional
	public void clear() {
		entityManager.clear();
		truncate();
	}

	private void truncate() {
		entityManager
			.createNativeQuery(String.format("SET FOREIGN_KEY_CHECKS %d", 0))
			.executeUpdate();

		tableNames.forEach(tableName ->
			entityManager
				.createNativeQuery(String.format("TRUNCATE TABLE %s", tableName))
				.executeUpdate()
		);

		entityManager
			.createNativeQuery(String.format("SET FOREIGN_KEY_CHECKS %d", 1))
			.executeUpdate();
	}
}