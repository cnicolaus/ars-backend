package com.prodyna.pac.ars.backend.air.service;

import javax.ejb.Stateless;

import com.prodyna.pac.ars.backend.common.test.AbstractDataLoader;

@Stateless
public class LicenceServiceTestDataLoader extends AbstractDataLoader {

	@Override
	protected String createSqlInitTestDataSet() {
		return "insert into aircraft_type (id, version, name) values ('1', 1, 'Cessna 172');"
				+ "insert into licence (id, version, expiry_date, user_id, aircraft_type) values ('1', 1, '2014-12-12 00:00:00', '1', '1');"
				+ "insert into licence (id, version, expiry_date, user_id, aircraft_type) values ('2', 1, '2014-12-12 00:00:00', '2', '1')";
	}

	@Override
	protected String createSqlCleanupTestDataSet() {
		return "delete from licence;delete from aircraft_type";
	}

}
