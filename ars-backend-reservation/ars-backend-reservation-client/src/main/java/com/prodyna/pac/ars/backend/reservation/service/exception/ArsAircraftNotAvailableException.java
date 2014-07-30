package com.prodyna.pac.ars.backend.reservation.service.exception;

import com.prodyna.pac.ars.backend.common.client.exception.ArsBusinessException;

public class ArsAircraftNotAvailableException extends ArsBusinessException {
	private static final long serialVersionUID = 1L;

	public ArsAircraftNotAvailableException() {
		super("Aircraft is not available");
	}

}
