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
public class LicenceServiceBean implements LicenceService {

	@Inject
	private EntityManager entityManager;

	@Override
	public void delete(@NotNull String id) {
		Licence licence = read(id);
		entityManager.remove(licence);
	}

	@Override
	public void create(@NotNull @Valid Licence licence) throws ArsDuplicateEntityException {
		List<Licence> result = findByUserIdAndAircraftTypeId(licence.getUserId(), licence.getAircraftType().getId());
		if (result.size() == 0) {
			entityManager.persist(licence);
		} else {
			throw new ArsDuplicateEntityException("licence already exists");
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Licence> findAllByUserId(@NotNull String userId) {
		return (List<Licence>) entityManager.createNamedQuery("licence.findByUserId").setParameter(1, userId)
				.getResultList();
	}

	@Override
	public Licence read(@NotNull String id) {
		return entityManager.find(Licence.class, id);
	}

	@SuppressWarnings("unchecked")
	private List<Licence> findByUserIdAndAircraftTypeId(String userId, String aircraftTypeId) {
		return (List<Licence>) entityManager.createNamedQuery("licence.findByUserIdAndAircraftTypeId")
				.setParameter(1, userId).setParameter(2, aircraftTypeId).getResultList();
	}
}
