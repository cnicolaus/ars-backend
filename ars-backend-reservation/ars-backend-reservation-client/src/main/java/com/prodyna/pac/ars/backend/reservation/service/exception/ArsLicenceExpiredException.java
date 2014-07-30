package com.prodyna.pac.ars.backend.reservation.service.exception;

import com.prodyna.pac.ars.backend.common.client.exception.ArsBusinessException;

public class ArsLicenceExpiredException extends ArsBusinessException {
	private static final long serialVersionUID = 1L;

	public ArsLicenceExpiredException() {
		super();
	}

	public ArsLicenceExpiredException(String message) {
		super(message);
	}

}
