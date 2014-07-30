package com.prodyna.pac.ars.backend.user.service;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.prodyna.pac.ars.backend.common.client.AbstractEntity;
import com.prodyna.pac.ars.backend.common.client.UserRole;
import com.prodyna.pac.ars.backend.common.client.UserRoleNames;
import com.prodyna.pac.ars.backend.common.client.exception.ArsBusinessException;
import com.prodyna.pac.ars.backend.common.client.exception.ArsDuplicateEntityException;
import com.prodyna.pac.ars.backend.common.client.exception.ArsRuntimeException;
import com.prodyna.pac.ars.backend.common.exception.ExceptionExtractor;
import com.prodyna.pac.ars.backend.common.producer.EntityManagerProducer;
import com.prodyna.pac.ars.backend.common.test.AbstractDataLoader;

@RunWith(Arquillian.class)
public class UserServiceBeanTest {

	@Inject
	private UserServiceBeanTestDataLoader dataLoader;

	@Inject
	private UserService service;

	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap.create(JavaArchive.class).addClass(AbstractDataLoader.class).addClass(UserRole.class)
				.addClass(ExceptionExtractor.class).addClass(AbstractEntity.class).addPackage(User.class.getPackage())
				.addClass(EntityManagerProducer.class).addClass(ArsRuntimeException.class)
				.addClass(ArsDuplicateEntityException.class).addClass(ArsBusinessException.class)
				.addClass(AuthenticatedUserServiceBeanMock.class).addClass(UserRoleNames.class)
				.addAsResource("persistence-test.xml", "META-INF/persistence.xml")
				.addAsResource("beans-test.xml", "META-INF/beans.xml");
	}

	@Before
	public void init() {
		dataLoader.init();
	}

	@After
	public void cleanUp() {
		dataLoader.cleanup();
	}

	@Test
	public void testCreateEmptyUsername() {
		try {
			User user = new User();
			user.setId("2");
			user.setUsername(null);
			user.setPassword("test12");
			user.setRole(UserRole.ADMIN);
			service.create(user);
		} catch (Exception e) {
			ConstraintViolationException ce = ExceptionExtractor.isException(e, ConstraintViolationException.class);
			Assert.assertNotNull(ce);
		}
	}

	@Test
	public void testCreate() throws ArsDuplicateEntityException {
		User user = new User();
		user.setId("2");
		user.setUsername("test123");
		user.setPassword("test12");
		user.setRole(UserRole.ADMIN);
		service.create(user);
		user = service.read("2");
		Assert.assertEquals("test123", user.getUsername());

	}

	@Test(expected = ArsDuplicateEntityException.class)
	public void testCreateDuplicateUser() throws ArsDuplicateEntityException {
		User user = new User();
		user.setId("2");
		user.setUsername("test12");
		user.setPassword("test12");
		user.setRole(UserRole.ADMIN);
		service.create(user);
	}

	@Test
	public void testRead() {
		User user = service.read("1");
		Assert.assertEquals(user.getUsername(), "test12");
	}

	@Test
	public void testDelete() {
		service.delete("1");
		Assert.assertEquals(0, service.findAll().size());
	}

	@Test
	public void testFindAll() {
		Assert.assertEquals(1, service.findAll().size());
	}

	@Test
	public void testReadAuthenticatedUser() {
		User user = service.readAuthenticatedUser();
		Assert.assertEquals(user.getUsername(), "test12");
	}

}
