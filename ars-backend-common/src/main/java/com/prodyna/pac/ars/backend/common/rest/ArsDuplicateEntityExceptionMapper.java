package com.prodyna.pac.ars.backend.common.rest;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.prodyna.pac.ars.backend.common.client.exception.ArsDuplicateEntityException;

@Provider
public class ArsDuplicateEntityExceptionMapper implements ExceptionMapper<ArsDuplicateEntityException> {
	@Override
	public Response toResponse(ArsDuplicateEntityException e) {
		return Response.status(531).entity(e.getMessage()).build();
	}
}
