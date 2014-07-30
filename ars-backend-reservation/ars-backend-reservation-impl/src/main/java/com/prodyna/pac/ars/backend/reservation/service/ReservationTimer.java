package com.prodyna.pac.ars.backend.reservation.service;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;

import org.slf4j.Logger;

import com.prodyna.pac.ars.backend.common.logging.Logged;
import com.prodyna.pac.ars.backend.monitoring.methodcall.MonitoredMethodCall;

@Singleton
@MonitoredMethodCall
@Logged
public class ReservationTimer {

	@Inject
	private Logger logger;

	@EJB
	private ReservationService reservationService;

	@Schedule(minute = "*/1", hour = "*", persistent = false)
	public void runEveryMinute() {
		try {
			reservationService.updateStateToReturned();
		} catch (Exception e) {
			logger.error("Timer execution failed", e);
		}
	}

}
