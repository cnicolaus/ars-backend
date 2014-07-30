package com.prodyna.pac.ars.backend.common.test;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public abstract class AbstractDataLoader {

	@Inject
	private EntityManager entityManager;

	public void init() {
		String[] sqlAry = createSqlInitTestDataSet().split(";");
		for (String sql : sqlAry) {
			entityManager.createNativeQuery(sql).executeUpdate();
		}
	}

	public void cleanup() {
		String[] sqlAry = createSqlCleanupTestDataSet().split(";");
		for (String sql : sqlAry) {
			entityManager.createNativeQuery(sql).executeUpdate();
		}
	}

	protected abstract String createSqlInitTestDataSet();

	protected abstract String createSqlCleanupTestDataSet();

}
