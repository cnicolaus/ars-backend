package com.prodyna.pac.ars.backend.air.service;

import javax.ejb.Stateless;

import com.prodyna.pac.ars.backend.common.test.AbstractDataLoader;

@Stateless
public class AircraftServiceTestDataLoader extends AbstractDataLoader {

	@Override
	protected String createSqlInitTestDataSet() {
		return "insert into aircraft_type (id, version, name) values ('1', 1, 'Cessna 172');"
				+ "insert into aircraft (id, version, registration_code, aircraft_type) values ('1', 1, 'D-EMIL', '1')";
	}

	@Override
	protected String createSqlCleanupTestDataSet() {
		return "delete from aircraft; delete from aircraft_type;";
	}

}
