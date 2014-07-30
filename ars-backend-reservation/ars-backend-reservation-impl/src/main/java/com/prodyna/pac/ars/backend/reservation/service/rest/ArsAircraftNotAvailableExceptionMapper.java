package com.prodyna.pac.ars.backend.reservation.service.rest;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.prodyna.pac.ars.backend.reservation.service.exception.ArsAircraftNotAvailableException;

@Provider
public class ArsAircraftNotAvailableExceptionMapper implements ExceptionMapper<ArsAircraftNotAvailableException> {
	@Override
	public Response toResponse(ArsAircraftNotAvailableException e) {
		return Response.status(535).entity(e.getMessage()).build();
	}
}
