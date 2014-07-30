package com.prodyna.pac.ars.backend.air.service;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.prodyna.pac.ars.backend.common.client.UserRoleNames;
import com.prodyna.pac.ars.backend.common.client.exception.ArsDuplicateEntityException;

@Path("licence")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface LicenceService {

	@PUT
	@Path("/")
	@RolesAllowed({ UserRoleNames.ADMIN })
	public void create(@NotNull @Valid Licence licence) throws ArsDuplicateEntityException;

	@GET
	@Path("/{id}")
	@RolesAllowed({ UserRoleNames.ADMIN, UserRoleNames.CHARTERER, UserRoleNames.GUEST })
	public Licence read(@NotNull @PathParam("id") String id);

	@DELETE
	@Path("/{id}")
	@RolesAllowed({ UserRoleNames.ADMIN })
	public void delete(@NotNull String id);

	@GET
	@Path("/user/{userId}")
	@RolesAllowed({ UserRoleNames.ADMIN, UserRoleNames.CHARTERER, UserRoleNames.GUEST })
	public List<Licence> findAllByUserId(@NotNull @PathParam("userId") String userId);

}
