package com.prodyna.pac.ars.backend.reservation.service;

import javax.ejb.Stateless;

import com.prodyna.pac.ars.backend.common.test.AbstractDataLoader;

@Stateless
public class ReservationBusinessServiceTestDataLoader extends AbstractDataLoader {

	@Override
	protected String createSqlInitTestDataSet() {
		return "insert into user (id, version, password, role, username) values ('1', '1', 'dd', 'CHARTERER', 'test12'); "
				+ "insert into user (id, version, password, role, username) values ('2', '1', 'dd', 'CHARTERER', 'test13'); "
				+ "insert into aircraft_type (id, version, name) values ('1', '1', 'Cessna 172');"
				+ "insert into aircraft_type (id, version, name) values ('2', '1', 'Cessna 152');"
				+ "insert into aircraft (id, version, registration_code, aircraft_type) values ('1', '1', 'D-EMIL', 1);"
				+ "insert into aircraft (id, version, registration_code, aircraft_type) values ('2', '1', 'D-EXIL', 2); "
				+ "insert into licence (id, version, user_id, aircraft_type, expiry_date) values ('1', '1', '1', '1', '2014-12-12');"
				+ "insert into licence (id, version, user_id, aircraft_type, expiry_date) values ('2', '1', '2', '1', '2014-12-12')";
	}

	@Override
	protected String createSqlCleanupTestDataSet() {
		return "delete from licence; " + "delete from user; " + "delete from aircraft; "
				+ "delete from aircraft_type; " + "delete from reservation";
	}

}
