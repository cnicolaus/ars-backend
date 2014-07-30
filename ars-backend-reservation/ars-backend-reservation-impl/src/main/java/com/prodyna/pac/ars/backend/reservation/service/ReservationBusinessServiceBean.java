package com.prodyna.pac.ars.backend.reservation.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.prodyna.pac.ars.backend.air.service.Aircraft;
import com.prodyna.pac.ars.backend.air.service.AircraftService;
import com.prodyna.pac.ars.backend.common.logging.Logged;
import com.prodyna.pac.ars.backend.monitoring.methodcall.MonitoredMethodCall;
import com.prodyna.pac.ars.backend.reservation.service.exception.ArsAircraftNotAvailableException;
import com.prodyna.pac.ars.backend.reservation.service.exception.ArsInvalidStateException;
import com.prodyna.pac.ars.backend.reservation.service.exception.ArsLicenceExpiredException;
import com.prodyna.pac.ars.backend.reservation.service.mapping.ReservationMapper;
import com.prodyna.pac.ars.backend.reservation.service.validation.ReservationValidator;
import com.prodyna.pac.ars.backend.user.service.User;
import com.prodyna.pac.ars.backend.user.service.UserService;

@Stateless
@MonitoredMethodCall
@Logged
public class ReservationBusinessServiceBean implements ReservationBusinessService {

	@Inject
	private ReservationValidator validator;

	@Inject
	private UserService userService;

	@Inject
	private AircraftService aircraftService;

	@Inject
	private ReservationService reservationService;

	@Inject
	private ReservationMapper reservationMapper;

	@Override
	public void create(@NotNull @Valid Reservation reservation) throws ArsAircraftNotAvailableException,
			ArsLicenceExpiredException {

		User user = userService.readAuthenticatedUser();

		validator.validateReservation(reservation, user.getId());

		Reservation existingReservation = reservationService.findByAircraftIdAndTimeInterval(
				reservation.getAircraftId(), reservation.getBeginDate(), reservation.getEndDate());
		if (existingReservation != null) {
			throw new ArsAircraftNotAvailableException();
		}

		reservation.setUserId(user.getId());
		reservation.setState(ReservationState.RESERVED);
		reservationService.create(reservation);
	}

	@Override
	public void update(Reservation reservation) throws ArsAircraftNotAvailableException, ArsLicenceExpiredException

	{
		User user = userService.readAuthenticatedUser();
		Reservation oldReservation = reservationService.read(reservation.getId());

		if (oldReservation.getState() == ReservationState.RESERVED) {
			validator.validateOwnerShip(reservation, user);
			validator.validateReservation(reservation, user.getId());

			Reservation existingReservation = reservationService.findByAircraftIdAndTimeInterval(
					reservation.getAircraftId(), reservation.getBeginDate(), reservation.getEndDate());
			if (existingReservation != null) {
				throw new ArsAircraftNotAvailableException();
			}

			oldReservation.setBeginDate(reservation.getBeginDate());
			oldReservation.setEndDate(reservation.getEndDate());
			reservationService.update(reservation);
		} else {
			throw new ArsInvalidStateException("Operation not allowed, state must be RESERVED");
		}
	}

	@Override
	public void cancel(String id) {

		User user = userService.readAuthenticatedUser();

		Reservation reservation = reservationService.read(id);
		if (reservation.getState() == ReservationState.RESERVED) {
			validator.validateOwnerShip(reservation, user);
			reservation.setState(ReservationState.CANCELLED);
			reservationService.update(reservation);
		} else {
			throw new ArsInvalidStateException("Operation not allowed, state must be RESERVED");
		}
	}

	@Override
	public void pickUpAircarft(String id) {

		User user = userService.readAuthenticatedUser();

		Reservation reservation = reservationService.read(id);
		if (reservation.getState() == ReservationState.RESERVED) {
			validator.validateOwnerShip(reservation, user);
			validator.validatePickupTime(reservation);
			reservation.setState(ReservationState.LENT);
			reservationService.update(reservation);
		} else {
			throw new ArsInvalidStateException("Operation not allowed, state must be RESERVED");
		}
	}

	@Override
	public void returnAircraft(String id) {

		User user = userService.readAuthenticatedUser();

		Reservation reservation = reservationService.read(id);
		if (reservation.getState() == ReservationState.LENT) {
			validator.validateOwnerShip(reservation, user);
			reservation.setState(ReservationState.RETURNED);
			reservationService.update(reservation);
		} else if (reservation.getState() == ReservationState.RETURNED) {
			// nichts tun
		} else {
			throw new ArsInvalidStateException("Operation not allowed, state must be LENT");
		}
	}

	@Override
	public List<AircraftReservationVO> findActive() {
		List<AircraftReservationVO> result = new ArrayList<AircraftReservationVO>();
		List<Reservation> reservations = reservationService.findActiveReservations();
		for (Reservation reservation : reservations) {
			User user = userService.read(reservation.getUserId());
			Aircraft aircraft = aircraftService.read(reservation.getAircraftId());
			AircraftReservationVO aircraftReservation = reservationMapper.mapToReservationVO(reservation, aircraft,
					user);
			result.add(aircraftReservation);
		}
		return result;
	}

	@Override
	public List<AircraftReservationVO> findActiveOfAuthenticatedUser() {
		User user = userService.readAuthenticatedUser();
		List<AircraftReservationVO> result = new ArrayList<AircraftReservationVO>();
		List<Reservation> reservations = reservationService.findActiveReservationsByUserId(user.getId());
		for (Reservation reservation : reservations) {
			Aircraft aircraft = aircraftService.read(reservation.getAircraftId());
			AircraftReservationVO aircraftReservation = reservationMapper.mapToReservationVO(reservation, aircraft,
					user);
			result.add(aircraftReservation);
		}
		return result;
	}

}
