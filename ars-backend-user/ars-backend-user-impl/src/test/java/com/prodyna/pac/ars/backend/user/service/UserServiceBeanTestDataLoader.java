package com.prodyna.pac.ars.backend.user.service;

import javax.ejb.Stateless;

import com.prodyna.pac.ars.backend.common.test.AbstractDataLoader;

@Stateless
public class UserServiceBeanTestDataLoader extends AbstractDataLoader {

	@Override
	protected String createSqlInitTestDataSet() {
		return "insert into user (id, version, password, role, username) values ('1', 1, 'dd', 'ADMIN', 'test12')";
	}

	@Override
	protected String createSqlCleanupTestDataSet() {
		return "delete from user";
	}

}
