package com.prodyna.pac.ars.backend.reservation.service;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Local
public interface ReservationService {

	public abstract void updateStateToReturned();

	public abstract void create(@NotNull @Valid Reservation reservation);

	public abstract Reservation read(@NotNull String id);

	public abstract void update(@NotNull @Valid Reservation reservation);

	public abstract List<Reservation> findActiveReservations();

	public abstract List<Reservation> findAll();

	public abstract void delete(@NotNull String id);

	public abstract Reservation findByAircraftIdAndTimeInterval(@NotNull String aircraftId, @NotNull Date beginDate,
			@NotNull Date endDate);

	public abstract List<Reservation> findActiveReservationsByUserId(@NotNull String userId);

}