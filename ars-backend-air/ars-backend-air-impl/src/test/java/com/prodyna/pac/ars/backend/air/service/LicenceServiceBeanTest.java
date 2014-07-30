package com.prodyna.pac.ars.backend.air.service;

import java.util.Date;

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
public class LicenceServiceBeanTest {

	@Inject
	private LicenceService licenceService;

	@Inject
	private LicenceServiceTestDataLoader dataLoader;

	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap.create(JavaArchive.class).addClass(AbstractDataLoader.class)
				.addClass(ExceptionExtractor.class).addClass(UserRole.class).addClass(AbstractEntity.class)
				.addClass(UserRoleNames.class).addClass(EntityManagerProducer.class)
				.addClass(ArsRuntimeException.class).addClass(ArsDuplicateEntityException.class)
				.addClass(ArsBusinessException.class).addPackage(Licence.class.getPackage())
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
	public void testCreateEmptyLicence() {
		try {
			licenceService.create(null);
		} catch (Exception e) {
			ConstraintViolationException ce = ExceptionExtractor.isException(e, ConstraintViolationException.class);
			Assert.assertNotNull(ce);
		}
	}

	@Test
	public void testCreateEmptyAircraftType() {
		try {
			Licence licence = new Licence();
			licence.setExpiryDate(new Date());
			licence.setUserId("1");
			licenceService.create(licence);
		} catch (Exception e) {
			ConstraintViolationException ce = ExceptionExtractor.isException(e, ConstraintViolationException.class);
			Assert.assertNotNull(ce);
		}
	}

	@Test
	public void testCreate() throws ArsDuplicateEntityException {
		Licence licence = new Licence();
		licence.setId("3");
		licence.setExpiryDate(new Date());
		licence.setUserId("3");

		AircraftType aircraftType = new AircraftType();
		aircraftType.setId("1");
		aircraftType.setName("Cessna 172");
		aircraftType.setVersion(1l);
		licence.setAircraftType(aircraftType);

		licenceService.create(licence);

		licence = licenceService.read("2");
		Assert.assertEquals("2", licence.getUserId());
	}

	@Test
	public void testRead() {
		Licence licence = licenceService.read("1");
		Assert.assertEquals("Cessna 172", licence.getAircraftType().getName());
	}

	@Test
	public void testDelete() {
		licenceService.delete("1");
		Assert.assertEquals(0, licenceService.findAllByUserId("1").size());
	}

	@Test
	public void testFindAllByUserId() {
		Assert.assertEquals(1, licenceService.findAllByUserId("1").size());
	}

}
