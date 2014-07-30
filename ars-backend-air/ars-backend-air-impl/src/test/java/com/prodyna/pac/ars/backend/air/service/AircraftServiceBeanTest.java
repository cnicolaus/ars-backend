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
public class AircraftServiceBeanTest {

	@Inject
	private AircraftService aircraftService;

	@Inject
	private AircraftServiceTestDataLoader dataLoader;

	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap.create(JavaArchive.class).addClass(AbstractDataLoader.class)
				.addClass(ExceptionExtractor.class).addClass(UserRole.class).addClass(AbstractEntity.class)
				.addClass(UserRoleNames.class).addClass(EntityManagerProducer.class)
				.addClass(ArsRuntimeException.class).addClass(ArsDuplicateEntityException.class)
				.addClass(ArsBusinessException.class).addPackage(Aircraft.class.getPackage())
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
	public void testCreateEmptyAircraft() {
		try {
			aircraftService.create(null);
		} catch (Exception e) {
			ConstraintViolationException ce = ExceptionExtractor.isException(e, ConstraintViolationException.class);
			Assert.assertNotNull(ce);
		}
	}

	@Test
	public void testCreateEmptyRegistrationCode() {
		try {
			Aircraft aircraft = new Aircraft();
			aircraft.setId("1");
			aircraft.setRegistrationCode("");

			AircraftType aircraftType = new AircraftType();
			aircraftType.setId("1");
			aircraftType.setName("Cessna 172");
			aircraft.setAircraftType(aircraftType);

			aircraftService.create(aircraft);
		} catch (Exception e) {
			ConstraintViolationException ce = ExceptionExtractor.isException(e, ConstraintViolationException.class);
			Assert.assertNotNull(ce);
		}
	}

	@Test
	public void testCreate() throws ArsDuplicateEntityException {
		Aircraft aircraft = new Aircraft();
		aircraft.setId("2");
		aircraft.setRegistrationCode("D-EXIL");

		AircraftType aircraftType = new AircraftType();
		aircraftType.setId("1");
		aircraftType.setName("Cessna 172");
		aircraftType.setVersion(1l);
		aircraft.setAircraftType(aircraftType);

		aircraftService.create(aircraft);

		aircraft = aircraftService.read("2");
		Assert.assertEquals("Cessna 172", aircraft.getAircraftType().getName());
		Assert.assertEquals("D-EXIL", aircraft.getRegistrationCode());
	}

	@Test(expected = ArsDuplicateEntityException.class)
	public void testCreateDuplicateEntity() throws ArsDuplicateEntityException {
		Aircraft aircraft = new Aircraft();
		aircraft.setId("2");
		aircraft.setRegistrationCode("D-EMIL");

		AircraftType aircraftType = new AircraftType();
		aircraftType.setId("1");
		aircraftType.setName("Cessna 172");
		aircraftType.setVersion(1l);
		aircraft.setAircraftType(aircraftType);

		aircraftService.create(aircraft);
	}

	@Test
	public void testRead() {
		Aircraft aircraft = aircraftService.read("1");
		Assert.assertEquals("D-EMIL", aircraft.getRegistrationCode());
	}

	@Test
	public void testUpdate() throws ArsDuplicateEntityException {
		Aircraft aircraft = new Aircraft();
		aircraft.setId("1");
		aircraft.setVersion(1l);
		aircraft.setRegistrationCode("D-EMIX");

		AircraftType aircraftType = new AircraftType();
		aircraftType.setId("1");
		aircraftType.setName("Cessna 172");
		aircraftType.setVersion(1l);
		aircraft.setAircraftType(aircraftType);

		aircraftService.update(aircraft);

		aircraft = aircraftService.read("1");
		Assert.assertEquals("D-EMIX", aircraft.getRegistrationCode());
	}

	@Test
	public void testDelete() {
		aircraftService.delete("1");
		Assert.assertEquals(0, aircraftService.findAll().size());
	}

	@Test
	public void testFindAll() {
		Assert.assertEquals(1, aircraftService.findAll().size());
	}

}
