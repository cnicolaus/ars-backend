package com.prodyna.pac.ars.backend.common.client.exception;


public class ArsRuntimeException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ArsRuntimeException(String message) {
		super(message);
	}

	public ArsRuntimeException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
