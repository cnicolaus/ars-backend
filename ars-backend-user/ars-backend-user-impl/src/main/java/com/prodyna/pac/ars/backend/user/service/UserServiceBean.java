package com.prodyna.pac.ars.backend.user.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.prodyna.pac.ars.backend.common.client.exception.ArsDuplicateEntityException;
import com.prodyna.pac.ars.backend.common.logging.Logged;
import com.prodyna.pac.ars.backend.monitoring.methodcall.MonitoredMethodCall;

@Stateless
@MonitoredMethodCall
@Logged
public class UserServiceBean implements UserService {

	@Inject
	private EntityManager entityManager;

	@Inject
	private AuthenticatedUserService authenticatedUserService;

	@Inject
	private PasswordEncrypter passwordEncrypter;

	@Override
	public void create(@NotNull @Valid User user) throws ArsDuplicateEntityException {
		checkDuplicate(user); // additional there is an unique key in database
		user.setPassword(passwordEncrypter.encryptPassword(user.getPassword()));
		entityManager.persist(user);
	}

	@Override
	public User read(@NotNull String id) {
		User user = entityManager.find(User.class, id);
		if (user != null) {
			entityManager.detach(user);
			user.setPassword(null);
		}
		return user;
	}

	@Override
	public void delete(@NotNull String id) {
		User user = entityManager.find(User.class, id);
		entityManager.remove(user);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<User> findAll() {
		List<User> resultList = (List<User>) entityManager.createNamedQuery("user.findAll").getResultList();
		for (User user : resultList) {
			entityManager.detach(user);
			user.setPassword(null);
		}
		return resultList;
	}

	@Override
	public User readAuthenticatedUser() {
		String username = authenticatedUserService.getCallerPrincipalName();
		User user = findUserByName(username);
		return user;
	}

	@SuppressWarnings("unchecked")
	private User findUserByName(String username) {
		User result = null;
		List<User> queryResult = entityManager.createNamedQuery("user.findByUsername").setParameter(1, username)
				.getResultList();

		if (queryResult.size() == 1) {
			result = (User) queryResult.get(0);
			entityManager.detach(result);
			result.setPassword(null);
		}
		return result;
	}

	private void checkDuplicate(User user) throws ArsDuplicateEntityException {
		User foundUser = findUserByName(user.getUsername());
		if (foundUser != null) {
			throw new ArsDuplicateEntityException(user.getUsername() + " already exists");
		}
	}

}
