package com.prodyna.pac.ars.backend.reservation.service.validation;

import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import com.prodyna.pac.ars.backend.air.service.Aircraft;
import com.prodyna.pac.ars.backend.air.service.AircraftService;
import com.prodyna.pac.ars.backend.air.service.Licence;
import com.prodyna.pac.ars.backend.air.service.LicenceService;
import com.prodyna.pac.ars.backend.common.client.exception.ArsRuntimeException;
import com.prodyna.pac.ars.backend.reservation.service.Reservation;
import com.prodyna.pac.ars.backend.reservation.service.exception.ArsLicenceExpiredException;
import com.prodyna.pac.ars.backend.user.service.User;

@Stateless
@LocalBean
public class ReservationValidator {

	@Inject
	private AircraftService aircraftService;

	@Inject
	private LicenceService licenceService;

	public void validateReservation(Reservation reservation, String userId) throws ArsLicenceExpiredException {
		validateInterval(reservation);
		validateLicences(reservation);
	}

	public void validateOwnerShip(Reservation reservation, User user) {
		if (!user.getId().equals(reservation.getUserId())) {
			throw new ArsRuntimeException("user has not ownership for this reservation");
		}
	}

	public void validatePickupTime(Reservation reservation) {
		Date now = new Date();
		if (now.before(reservation.getBeginDate()) || now.after(reservation.getEndDate())) {
			throw new ArsRuntimeException("Not allowed, beginDate of reservation is in the future");
		}
	}

	private void validateLicences(Reservation reservation) throws ArsLicenceExpiredException {
		boolean licensed = false;
		Aircraft aircraft = aircraftService.read(reservation.getAircraftId());

		List<Licence> licences = licenceService.findAllByUserId(reservation.getUserId());
		for (Licence licence : licences) {
			if (licence.getAircraftType().equals(aircraft.getAircraftType())) {
				if (licence.getExpiryDate().before(reservation.getEndDate())) {
					throw new ArsLicenceExpiredException();
				}
				licensed = true;
			}
		}

		if (!licensed) {
			throw new ArsRuntimeException("not licensed for aircraft type");
		}

	}

	private void validateInterval(Reservation reservation) {
		if (reservation.getEndDate().getTime() - reservation.getBeginDate().getTime() < 1000 * 60) {
			throw new ArsRuntimeException("beginDate is after endDate");
		}

		if (!reservation.getEndDate().after(new Date())) {
			throw new ArsRuntimeException("endDate is in the past");
		}
	}

}
