package com.prodyna.pac.ars.backend.common.client.exception;

import javax.ejb.ApplicationException;

@ApplicationException
public class ArsBusinessException extends Exception {
	private static final long serialVersionUID = 1L;

	public ArsBusinessException(String message) {
		super(message);
	}

	public ArsBusinessException() {
		super();
	}

}
