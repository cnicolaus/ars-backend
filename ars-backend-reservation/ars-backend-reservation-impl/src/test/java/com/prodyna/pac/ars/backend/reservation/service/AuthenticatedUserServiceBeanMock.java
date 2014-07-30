package com.prodyna.pac.ars.backend.reservation.service;

import javax.enterprise.inject.Alternative;

import com.prodyna.pac.ars.backend.user.service.AuthenticatedUserService;

@Alternative
public class AuthenticatedUserServiceBeanMock implements AuthenticatedUserService {

	@Override
	public String getCallerPrincipalName() {
		return "test12";
	}

}
