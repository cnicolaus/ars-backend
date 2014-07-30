package com.prodyna.pac.ars.backend.reservation.service;

import java.util.Calendar;
import java.util.Date;

import javax.ejb.EJBException;
import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.prodyna.pac.ars.backend.common.client.exception.ArsDuplicateEntityException;
import com.prodyna.pac.ars.backend.common.client.exception.ArsRuntimeException;
import com.prodyna.pac.ars.backend.common.exception.ExceptionExtractor;
import com.prodyna.pac.ars.backend.reservation.service.exception.ArsAircraftNotAvailableException;
import com.prodyna.pac.ars.backend.reservation.service.exception.ArsInvalidStateException;
import com.prodyna.pac.ars.backend.reservation.service.exception.ArsLicenceExpiredException;

@RunWith(Arquillian.class)
public class ReservationBusinessServiceBeanTest {

	@Inject
	private ReservationBusinessService reservationService;

	@Inject
	private ReservationBusinessServiceTestDataLoader dataLoader;

	@Deployment
	public static JavaArchive createDeployment() {
		return ArchiveCreator.createArchive();
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
	public void testCreate() throws ArsAircraftNotAvailableException, ArsDuplicateEntityException,
			ArsLicenceExpiredException {

		Reservation reservation = createReservation();
		reservationService.create(reservation);
	}

	@Test
	public void testCreateInvalidInterval() throws ArsAircraftNotAvailableException, ArsDuplicateEntityException,
			ArsLicenceExpiredException {

		try {
			Reservation reservation = createReservation();
			reservation.setBeginDate(new Date(System.currentTimeMillis() + 1000 * 60 * 5));
			reservation.setEndDate(new Date());
			reservationService.create(reservation);
		} catch (EJBException e) {
			ArsRuntimeException ce = ExceptionExtractor.isException(e, ArsRuntimeException.class);
			Assert.assertNotNull(ce);
		}

	}

	@Test
	public void testCreateEndDateInPast() throws ArsAircraftNotAvailableException, ArsDuplicateEntityException,
			ArsLicenceExpiredException {

		try {
			Reservation reservation = createReservation();
			reservation.setBeginDate(new Date(System.currentTimeMillis() - 1000 * 60 * 5));
			reservation.setEndDate(new Date(System.currentTimeMillis() - 1000 * 60 * 4));
			reservationService.create(reservation);
		} catch (EJBException e) {
			ArsRuntimeException ce = ExceptionExtractor.isException(e, ArsRuntimeException.class);
			Assert.assertNotNull(ce);
		}

	}

	@Test(expected = ArsLicenceExpiredException.class)
	public void testCreateInvalidLicenceDate() throws ArsAircraftNotAvailableException, ArsDuplicateEntityException,
			ArsLicenceExpiredException {

		Reservation reservation = createReservation();
		reservation.setUserId("2");
		reservation.setBeginDate(new Date(System.currentTimeMillis() + 1000 * 60 * 1));
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2015);
		reservation.setEndDate(cal.getTime());
		reservationService.create(reservation);

	}

	@Test(expected = ArsAircraftNotAvailableException.class)
	public void testCreateAircraftNotAvailable() throws ArsAircraftNotAvailableException, ArsDuplicateEntityException,
			ArsLicenceExpiredException {

		Reservation reservation = createReservation();
		reservation.setId("2");
		reservation.setBeginDate(new Date(System.currentTimeMillis() + 5 * 60 * 1000));
		reservation.setEndDate(new Date(System.currentTimeMillis() + 25 * 60 * 1000));
		reservationService.create(reservation);

		reservation = createReservation();
		reservation.setId("3");
		reservation.setBeginDate(new Date(System.currentTimeMillis() + 10 * 60 * 1000));
		reservation.setEndDate(new Date(System.currentTimeMillis() + 25 * 60 * 1000));

		reservationService.create(reservation);
	}

	@Test
	public void testCreateNoLicence() throws ArsAircraftNotAvailableException, ArsDuplicateEntityException,
			ArsLicenceExpiredException {

		try {
			Reservation reservation = createReservation();
			reservation.setAircraftId("2");
			reservationService.create(reservation);
		} catch (EJBException e) {
			ArsRuntimeException ce = ExceptionExtractor.isException(e, ArsRuntimeException.class);
			Assert.assertNotNull(ce);
		}

	}

	@Test
	public void testPickUp() throws ArsDuplicateEntityException, ArsAircraftNotAvailableException,
			ArsLicenceExpiredException {

		Reservation reservation = createReservation();
		reservationService.create(reservation);

		reservationService.pickUpAircarft("1");

	}

	@Test
	public void testCancel() throws ArsDuplicateEntityException, ArsAircraftNotAvailableException,
			ArsLicenceExpiredException {

		Reservation reservation = createReservation();
		reservationService.create(reservation);

		reservationService.cancel("1");

	}

	@Test
	public void testReturn() throws ArsDuplicateEntityException, ArsAircraftNotAvailableException,
			ArsLicenceExpiredException {

		Reservation reservation = createReservation();
		reservationService.create(reservation);

		reservationService.pickUpAircarft("1");

		reservationService.returnAircraft("1");

	}

	@Test
	public void testReturnAircraftInvalidState() throws ArsDuplicateEntityException, ArsAircraftNotAvailableException,
			ArsLicenceExpiredException {

		try {
			Reservation reservation = createReservation();
			reservationService.create(reservation);

			reservationService.returnAircraft("1");

		} catch (EJBException e) {
			ArsInvalidStateException ce = ExceptionExtractor.isException(e, ArsInvalidStateException.class);
			Assert.assertNotNull(ce);
		}

	}

	@Test
	public void testPickUpCharterInvalidPickupTime() throws ArsDuplicateEntityException,
			ArsAircraftNotAvailableException, ArsLicenceExpiredException {

		try {
			Reservation reservation = createReservation();
			reservation.setBeginDate(new Date(System.currentTimeMillis() + 5 * 60 * 1000));
			reservation.setEndDate(new Date(System.currentTimeMillis() + 25 * 60 * 1000));
			reservationService.create(reservation);

			reservationService.pickUpAircarft("1");
		} catch (EJBException e) {
			ArsRuntimeException ce = ExceptionExtractor.isException(e, ArsRuntimeException.class);
			Assert.assertNotNull(ce);
		}

	}

	@Test
	public void testPickUpAircraftInvalidChartererId() throws ArsDuplicateEntityException,
			ArsAircraftNotAvailableException, ArsLicenceExpiredException {

		try {
			Reservation reservation = createReservation();
			reservation.setBeginDate(new Date(System.currentTimeMillis() - 5 * 60 * 1000));
			reservation.setEndDate(new Date(System.currentTimeMillis() + 25 * 60 * 1000));
			reservation.setUserId("100");
			reservationService.create(reservation);

			reservationService.pickUpAircarft("1");
		} catch (EJBException e) {
			ArsRuntimeException ce = ExceptionExtractor.isException(e, ArsRuntimeException.class);
			Assert.assertNotNull(ce);
		}

	}

	@Test
	public void testPickUpCharterObjectInvalidState() throws ArsDuplicateEntityException,
			ArsAircraftNotAvailableException, ArsLicenceExpiredException {

		try {
			Reservation reservation = createReservation();
			reservationService.create(reservation);

			reservationService.pickUpAircarft("1");

			reservationService.pickUpAircarft("1");

		} catch (EJBException e) {
			ArsRuntimeException ce = ExceptionExtractor.isException(e, ArsRuntimeException.class);
			Assert.assertNotNull(ce);
		}
	}

	private Reservation createReservation() {
		Reservation reservation = new Reservation();
		reservation.setId("1");
		reservation.setBeginDate(new Date(System.currentTimeMillis() - 5000));
		reservation.setUserId("1");
		reservation.setAircraftId("1");
		reservation.setEndDate(new Date(System.currentTimeMillis() + 25 * 60 * 1000));
		reservation.setState(ReservationState.RESERVED);
		return reservation;
	}

}
