package com.prodyna.pac.ars.backend.user.service;

import javax.ejb.SessionContext;
import javax.enterprise.inject.Alternative;
import javax.naming.InitialContext;
import javax.naming.NamingException;

@Alternative
public class AuthenticatedUserServiceBean implements AuthenticatedUserService {

	@Override
	public String getCallerPrincipalName() {
		// WORKAROUND: Injection of SessionContext seems not to work here!
		try {
			InitialContext ic = new InitialContext();
			SessionContext sessionContext = (SessionContext) ic.lookup("java:comp/EJBContext");
			return sessionContext.getCallerPrincipal().getName();
		} catch (NamingException e) {
			throw new RuntimeException(e);
		}
	}

}
