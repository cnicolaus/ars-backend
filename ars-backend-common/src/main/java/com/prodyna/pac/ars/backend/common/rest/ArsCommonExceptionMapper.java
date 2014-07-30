package com.prodyna.pac.ars.backend.common.rest;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ArsCommonExceptionMapper implements ExceptionMapper<Exception> {
	@Override
	public Response toResponse(Exception e) {
		return Response.status(500).entity(e.getMessage()).build();
	}
}
