package com.prodyna.pac.ars.backend.reservation.service.mapping;

import java.util.Date;

import com.prodyna.pac.ars.backend.air.service.Aircraft;
import com.prodyna.pac.ars.backend.reservation.service.AircraftReservationVO;
import com.prodyna.pac.ars.backend.reservation.service.Reservation;
import com.prodyna.pac.ars.backend.user.service.User;

public class ReservationMapper {

	/**
	 * 
	 * @param reservation
	 * @param aircraft
	 * @param user
	 * @return
	 */
	public AircraftReservationVO mapToReservationVO(Reservation reservation, Aircraft aircraft, User user) {
		AircraftReservationVO aircraftReservation = new AircraftReservationVO();
		aircraftReservation.setId(reservation.getId());
		aircraftReservation.setBeginDate(reservation.getBeginDate());
		aircraftReservation.setEndDate(reservation.getEndDate());
		aircraftReservation.setRegistrationCode(aircraft.getRegistrationCode());
		aircraftReservation.setStatus(reservation.getState().name());
		aircraftReservation.setAircraftType(aircraft.getAircraftType().getName());
		aircraftReservation.setUserName(user.getUsername());
		aircraftReservation.setPickUpTimeAchieved(reservation.getBeginDate().before(new Date()));
		return aircraftReservation;
	}

}
