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
public class AircraftTypeServiceBean implements AircraftTypeService {

	@Inject
	private EntityManager entityManager;

	@Override
	public void create(@NotNull @Valid AircraftType aircraftType) throws ArsDuplicateEntityException {
		List<AircraftType> result = findByName(aircraftType); // additional there is an unique key in database
		if (result.size() == 0) {
			entityManager.persist(aircraftType);
		} else {
			throw new ArsDuplicateEntityException(aircraftType.getName() + " already exists");
		}
	}

	@Override
	public AircraftType read(@NotNull String id) {
		return entityManager.find(AircraftType.class, id);
	}

	@Override
	public void update(@NotNull @Valid AircraftType aircraftType) throws ArsDuplicateEntityException {
		List<AircraftType> result = findByName(aircraftType);
		if (result.size() == 0) {
			entityManager.merge(aircraftType);
		} else {
			throw new ArsDuplicateEntityException(aircraftType.getName() + " already exists");
		}
	}

	@Override
	public void delete(@NotNull String id) {
		AircraftType aircraftType = read(id);
		entityManager.remove(aircraftType);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<AircraftType> findAll() {
		return (List<AircraftType>) entityManager.createNamedQuery("aircraftType.findAll").getResultList();
	}

	@SuppressWarnings("unchecked")
	private List<AircraftType> findByName(AircraftType aircraftType) {
		return entityManager.createNamedQuery("aircraftType.findByName").setParameter(1, aircraftType.getName())
				.getResultList();
	}

}
