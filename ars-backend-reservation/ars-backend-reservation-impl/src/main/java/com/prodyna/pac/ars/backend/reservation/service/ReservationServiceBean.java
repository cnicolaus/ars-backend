package com.prodyna.pac.ars.backend.reservation.service;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.prodyna.pac.ars.backend.common.logging.Logged;
import com.prodyna.pac.ars.backend.monitoring.methodcall.MonitoredMethodCall;

@Stateless
@Logged
@MonitoredMethodCall
public class ReservationServiceBean implements ReservationService {

	@Inject
	private EntityManager entityManager;

	@Override
	public void updateStateToReturned() {
		entityManager.createNamedQuery("reservation.updateState").setParameter(1, ReservationState.RETURNED)
				.setParameter(2, ReservationState.LENT).setParameter(3, ReservationState.RESERVED)
				.setParameter(4, new Date()).executeUpdate();
	}

	@Override
	public void create(@NotNull @Valid Reservation reservation) {
		entityManager.persist(reservation);
	}

	@Override
	public Reservation read(@NotNull String id) {
		return entityManager.find(Reservation.class, id);
	}

	@Override
	public void update(@NotNull @Valid Reservation reservation) {
		entityManager.merge(reservation);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Reservation> findActiveReservations() {
		return (List<Reservation>) entityManager.createNamedQuery("reservation.findByState")
				.setParameter(1, ReservationState.RESERVED).setParameter(2, ReservationState.LENT).getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public Reservation findByAircraftIdAndTimeInterval(@NotNull String aircraftId, @NotNull Date beginDate,
			@NotNull Date endDate) {

		List<Reservation> result = (List<Reservation>) entityManager
				.createNamedQuery("reservation.findByAircraftIdAndInterval").setParameter(1, aircraftId)
				.setParameter(2, ReservationState.RESERVED).setParameter(3, ReservationState.LENT)
				.setParameter(4, beginDate).setParameter(5, beginDate).setParameter(6, endDate)
				.setParameter(7, endDate).setParameter(8, beginDate).setParameter(9, endDate).getResultList();

		if (result.size() > 0) {
			return result.get(0);
		} else {
			return null;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Reservation> findActiveReservationsByUserId(@NotNull String userId) {
		return (List<Reservation>) entityManager.createNamedQuery("reservation.findByUserIdAndState")
				.setParameter(1, userId).setParameter(2, ReservationState.RESERVED)
				.setParameter(3, ReservationState.LENT).getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Reservation> findAll() {
		return (List<Reservation>) entityManager.createNamedQuery("reservation.findAll").getResultList();
	}

	@Override
	public void delete(@NotNull String id) {
		Reservation reservation = read(id);
		entityManager.remove(reservation);
	}

}
