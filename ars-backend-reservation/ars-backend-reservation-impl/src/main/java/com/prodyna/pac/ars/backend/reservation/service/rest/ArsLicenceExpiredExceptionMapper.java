package com.prodyna.pac.ars.backend.reservation.service.rest;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.prodyna.pac.ars.backend.reservation.service.exception.ArsLicenceExpiredException;

@Provider
public class ArsLicenceExpiredExceptionMapper implements ExceptionMapper<ArsLicenceExpiredException> {
	@Override
	public Response toResponse(ArsLicenceExpiredException e) {
		return Response.status(540).entity(e.getMessage()).build();
	}
}
