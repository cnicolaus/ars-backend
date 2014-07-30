package com.prodyna.pac.ars.backend.air.service;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.prodyna.pac.ars.backend.common.client.UserRoleNames;
import com.prodyna.pac.ars.backend.common.client.exception.ArsDuplicateEntityException;

@Path("aircraft_type")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface AircraftTypeService {

	@PUT
	@Path("/")
	@RolesAllowed({ UserRoleNames.ADMIN })
	public void create(@NotNull @Valid AircraftType aircraftType) throws ArsDuplicateEntityException;

	@GET
	@Path("/{id}")
	@RolesAllowed({ UserRoleNames.ADMIN, UserRoleNames.CHARTERER, UserRoleNames.GUEST })
	public AircraftType read(@NotNull @PathParam("id") String id);

	@POST
	@Path("/")
	@RolesAllowed({ UserRoleNames.ADMIN })
	public void update(@NotNull @Valid AircraftType aircraftType) throws ArsDuplicateEntityException;

	@DELETE
	@Path("/{id}")
	@RolesAllowed({ UserRoleNames.ADMIN })
	public void delete(@NotNull String id);

	@GET
	@Path("/")
	@RolesAllowed({ UserRoleNames.ADMIN, UserRoleNames.CHARTERER, UserRoleNames.GUEST })
	public List<AircraftType> findAll();

}
