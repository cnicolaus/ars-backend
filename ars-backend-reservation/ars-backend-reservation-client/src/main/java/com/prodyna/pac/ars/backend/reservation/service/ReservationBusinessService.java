package com.prodyna.pac.ars.backend.reservation.service;

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
import com.prodyna.pac.ars.backend.reservation.service.exception.ArsAircraftNotAvailableException;
import com.prodyna.pac.ars.backend.reservation.service.exception.ArsLicenceExpiredException;

@Path("reservation")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface ReservationBusinessService {

	@PUT
	@Path("/")
	@RolesAllowed({ UserRoleNames.ADMIN, UserRoleNames.CHARTERER })
	public void create(@NotNull @Valid Reservation reservation) throws ArsAircraftNotAvailableException,
			ArsLicenceExpiredException;

	@POST
	@Path("/")
	@RolesAllowed({ UserRoleNames.ADMIN, UserRoleNames.CHARTERER })
	public void update(@NotNull @Valid Reservation reservation) throws ArsAircraftNotAvailableException,
			ArsLicenceExpiredException;

	@DELETE
	@Path("/{id}")
	@RolesAllowed({ UserRoleNames.ADMIN, UserRoleNames.CHARTERER })
	public void cancel(@NotNull @PathParam("id") String id);

	@GET
	@Path("/pickup/{id}")
	@RolesAllowed({ UserRoleNames.ADMIN, UserRoleNames.CHARTERER })
	public void pickUpAircarft(@NotNull @PathParam("id") String id);

	@GET
	@Path("/return/{id}")
	@RolesAllowed({ UserRoleNames.ADMIN, UserRoleNames.CHARTERER })
	public void returnAircraft(@NotNull @PathParam("id") String id);

	@GET
	@Path("/all_user")
	@RolesAllowed({ UserRoleNames.ADMIN, UserRoleNames.CHARTERER, UserRoleNames.GUEST })
	public List<AircraftReservationVO> findActive();

	@GET
	@Path("/auth_user")
	@RolesAllowed({ UserRoleNames.ADMIN, UserRoleNames.CHARTERER, UserRoleNames.GUEST })
	public List<AircraftReservationVO> findActiveOfAuthenticatedUser();
}
