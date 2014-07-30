package com.prodyna.pac.ars.backend.air.service;

import javax.ejb.Stateless;

import com.prodyna.pac.ars.backend.common.test.AbstractDataLoader;

@Stateless
public class AircraftTypeServiceTestDataLoader extends AbstractDataLoader {

	@Override
	protected String createSqlInitTestDataSet() {
		return "insert into aircraft_type (id, version, name) values ('1', 1, 'Cessna 172');";
	}

	@Override
	protected String createSqlCleanupTestDataSet() {
		return "delete from aircraft_type;";
	}

}
