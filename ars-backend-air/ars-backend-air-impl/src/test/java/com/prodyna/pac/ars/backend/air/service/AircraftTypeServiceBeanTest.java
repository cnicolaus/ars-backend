package com.prodyna.pac.ars.backend.air.service;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
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
public class AircraftTypeServiceBeanTest {

	@Inject
	private AircraftTypeService service;

	@Inject
	private AircraftTypeServiceTestDataLoader dataLoader;

	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap.create(JavaArchive.class).addClass(AbstractDataLoader.class)
				.addClass(ExceptionExtractor.class).addClass(UserRole.class).addClass(AbstractEntity.class)
				.addClass(UserRoleNames.class).addClass(EntityManagerProducer.class)
				.addClass(ArsRuntimeException.class).addClass(ArsDuplicateEntityException.class)
				.addClass(ArsBusinessException.class).addPackage(AircraftType.class.getPackage())
				.addAsResource("persistence-test.xml", "META-INF/persistence.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
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
	public void testCreateEmptyAircraftType() {
		try {
			service.create(null);
		} catch (Exception e) {

			ConstraintViolationException ce = ExceptionExtractor.isException(e, ConstraintViolationException.class);
			Assert.assertNotNull(ce);
		}
	}

	@Test
	public void testCreateEmptyId() {
		try {
			AircraftType aircraftType = new AircraftType();
			aircraftType.setId(null);
			aircraftType.setName("Cessna 172");
			service.create(aircraftType);
		} catch (Exception e) {
			ConstraintViolationException ce = ExceptionExtractor.isException(e, ConstraintViolationException.class);
			Assert.assertNotNull(ce);
		}
	}

	@Test
	public void testCreateEmptyName() {
		try {
			AircraftType aircraftType = new AircraftType();
			aircraftType.setId("1");
			aircraftType.setName("");
			service.create(aircraftType);
		} catch (Exception e) {
			ConstraintViolationException ce = ExceptionExtractor.isException(e, ConstraintViolationException.class);
			Assert.assertNotNull(ce);
		}
	}

	@Test
	public void testCreate() throws ArsDuplicateEntityException {
		AircraftType aircraftType = new AircraftType();
		aircraftType.setId("2");
		aircraftType.setName("Cessna 152");
		service.create(aircraftType);
		aircraftType = service.read("2");
		Assert.assertEquals("Cessna 152", aircraftType.getName());
	}

	@Test(expected = ArsDuplicateEntityException.class)
	public void testCreateDuplicateEntity() throws ArsDuplicateEntityException {
		AircraftType aircraftType = new AircraftType();
		aircraftType.setId("2");
		aircraftType.setName("Cessna 172");
		service.create(aircraftType);
	}

	@Test
	public void testRead() {
		AircraftType aircraftType = service.read("1");
		Assert.assertEquals("Cessna 172", aircraftType.getName());
	}

	@Test
	public void testUpdate() throws ArsDuplicateEntityException {
		AircraftType aircraftType = new AircraftType();
		aircraftType.setId("1");
		aircraftType.setVersion(1l);
		aircraftType.setName("Cessna 172a");
		service.update(aircraftType);

		aircraftType = service.read("1");
		Assert.assertEquals("Cessna 172a", aircraftType.getName());
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

}
