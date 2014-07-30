package com.prodyna.pac.ars.backend.air.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.prodyna.pac.ars.backend.common.client.exception.ArsDuplicateEntityException;
import com.prodyna.pac.ars.backend.common.logging.Logged;
import com.prodyna.pac.ars.backend.monitoring.methodcall.MonitoredMethodCall;

@Stateless
@MonitoredMethodCall
@Logged
public class AircraftServiceBean implements AircraftService {

	@Inject
	private EntityManager entityManager;

	@Override
	public void create(@NotNull @Valid Aircraft aircraft) throws ArsDuplicateEntityException {
		List<Aircraft> result = findByRegistrationCode(aircraft); // additional there is an unique key in database
		if (result.size() == 0) {
			entityManager.persist(aircraft);
		} else {
			throw new ArsDuplicateEntityException(aircraft.getRegistrationCode() + " already exists");
		}
	}

	@Override
	public Aircraft read(@NotNull String id) {
		return entityManager.find(Aircraft.class, id);
	}

	@Override
	public void update(@NotNull @Valid Aircraft aircraft) throws ArsDuplicateEntityException {
		List<Aircraft> result = findByRegistrationCode(aircraft);
		if (result.size() == 0) {
			entityManager.merge(aircraft);
		} else {
			throw new ArsDuplicateEntityException(aircraft.getRegistrationCode() + " already exists");
		}
	}

	@Override
	public void delete(@NotNull String id) {
		Aircraft aircraft = read(id);
		entityManager.remove(aircraft);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Aircraft> findAll() {
		return (List<Aircraft>) entityManager.createNamedQuery("aircraft.findAll").getResultList();
	}

	@SuppressWarnings("unchecked")
	private List<Aircraft> findByRegistrationCode(Aircraft aircraft) {
		return entityManager.createNamedQuery("aircraft.findByRegistrationCode")
				.setParameter(1, aircraft.getRegistrationCode()).getResultList();
	}

}
