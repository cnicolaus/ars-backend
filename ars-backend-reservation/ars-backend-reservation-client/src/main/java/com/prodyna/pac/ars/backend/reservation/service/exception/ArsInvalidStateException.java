package com.prodyna.pac.ars.backend.reservation.service.exception;

import com.prodyna.pac.ars.backend.common.client.exception.ArsRuntimeException;

public class ArsInvalidStateException extends ArsRuntimeException {
	private static final long serialVersionUID = 1L;

	public ArsInvalidStateException(String message) {
		super(message);
	}

}
