package com.prodyna.pac.ars.backend.reservation.service;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class ReservationServiceBeanTest {

	@Inject
	private ReservationService reservationService;

	@Deployment
	public static JavaArchive createDeployment() {
		return ArchiveCreator.createArchive();
	}

	@Test
	@InSequence(1)
	public void testUpdateState() {
		Reservation reservation = createReservation();
		reservation.setState(ReservationState.LENT);
		reservation.setBeginDate(new Date(System.currentTimeMillis() - 5 * 60 * 1000));
		reservation.setEndDate(new Date(System.currentTimeMillis() - 4 * 60 * 1000));
		reservationService.create(reservation);

		reservation = createReservation();
		reservation.setId("2");
		reservation.setState(ReservationState.LENT);
		reservation.setBeginDate(new Date(System.currentTimeMillis() + 5 * 60 * 1000));
		reservation.setEndDate(new Date(System.currentTimeMillis() + 6 * 60 * 1000));
		reservationService.create(reservation);

		reservationService.updateStateToReturned();

		int numberStatusReturned = 0;
		List<Reservation> result = reservationService.findAll();
		for (Reservation item : result) {
			if (item.getState() == ReservationState.RETURNED) {
				numberStatusReturned++;
			}
		}
		Assert.assertEquals(1, numberStatusReturned);
	}

	@Test
	@InSequence(2)
	public void testFindByAircraftIdAndTimeInterval() {

		Reservation reservation = createReservation();
		reservation.setState(ReservationState.RESERVED);
		reservation.setBeginDate(new Date(System.currentTimeMillis() + 5 * 60 * 1000));
		reservation.setEndDate(new Date(System.currentTimeMillis() + 10 * 60 * 1000));
		reservationService.create(reservation);

		reservation = createReservation();
		reservation.setId("2");
		reservation.setState(ReservationState.CANCELLED);
		reservation.setBeginDate(new Date(System.currentTimeMillis() + 5 * 60 * 1000));
		reservation.setEndDate(new Date(System.currentTimeMillis() + 10 * 60 * 1000));
		reservationService.create(reservation);

		reservation = createReservation();
		reservation.setId("3");
		reservation.setState(ReservationState.RESERVED);
		reservation.setAircraftId("2");
		reservation.setBeginDate(new Date(System.currentTimeMillis() + 5 * 60 * 1000));
		reservation.setEndDate(new Date(System.currentTimeMillis() + 10 * 60 * 1000));
		reservationService.create(reservation);

		Reservation result = reservationService.findByAircraftIdAndTimeInterval("1",
				new Date(System.currentTimeMillis() - 6 * 60 * 1000), new Date(
						System.currentTimeMillis() - 5 * 60 * 1000));
		Assert.assertNull(result);
	}

	@Test
	@InSequence(2)
	public void testFindByAircraftIdAndTimeInterval2() {

		Reservation reservation = createReservation();
		reservation.setState(ReservationState.RESERVED);
		reservation.setBeginDate(new Date(System.currentTimeMillis() + 5 * 60 * 1000));
		reservation.setEndDate(new Date(System.currentTimeMillis() + 10 * 60 * 1000));
		reservationService.create(reservation);

		reservation = createReservation();
		reservation.setId("2");
		reservation.setState(ReservationState.CANCELLED);
		reservation.setBeginDate(new Date(System.currentTimeMillis() + 5 * 60 * 1000));
		reservation.setEndDate(new Date(System.currentTimeMillis() + 10 * 60 * 1000));
		reservationService.create(reservation);

		reservation = createReservation();
		reservation.setId("3");
		reservation.setState(ReservationState.RESERVED);
		reservation.setAircraftId("2");
		reservation.setBeginDate(new Date(System.currentTimeMillis() + 5 * 60 * 1000));
		reservation.setEndDate(new Date(System.currentTimeMillis() + 10 * 60 * 1000));
		reservationService.create(reservation);

		Reservation result = reservationService.findByAircraftIdAndTimeInterval("1",
				new Date(System.currentTimeMillis() + 4 * 60 * 1000), new Date(
						System.currentTimeMillis() + 6 * 60 * 1000));
		Assert.assertNotNull(result);
	}

	@Test
	@InSequence(2)
	public void testFindByAircraftIdAndTimeInterval3() {

		Reservation reservation = createReservation();
		reservation.setState(ReservationState.RESERVED);
		reservation.setBeginDate(new Date(System.currentTimeMillis() + 5 * 60 * 1000));
		reservation.setEndDate(new Date(System.currentTimeMillis() + 10 * 60 * 1000));
		reservationService.create(reservation);

		reservation = createReservation();
		reservation.setId("2");
		reservation.setState(ReservationState.CANCELLED);
		reservation.setBeginDate(new Date(System.currentTimeMillis() + 5 * 60 * 1000));
		reservation.setEndDate(new Date(System.currentTimeMillis() + 10 * 60 * 1000));
		reservationService.create(reservation);

		reservation = createReservation();
		reservation.setId("3");
		reservation.setState(ReservationState.RESERVED);
		reservation.setAircraftId("2");
		reservation.setBeginDate(new Date(System.currentTimeMillis() + 5 * 60 * 1000));
		reservation.setEndDate(new Date(System.currentTimeMillis() + 10 * 60 * 1000));
		reservationService.create(reservation);

		Reservation result = reservationService.findByAircraftIdAndTimeInterval("1",
				new Date(System.currentTimeMillis() + 7 * 60 * 1000), new Date(
						System.currentTimeMillis() + 15 * 60 * 1000));
		Assert.assertNotNull(result);
	}

	@Test
	@InSequence(2)
	public void testFindByAircraftIdAndTimeInterval4() {

		Reservation reservation = createReservation();
		reservation.setState(ReservationState.RESERVED);
		reservation.setBeginDate(new Date(System.currentTimeMillis() + 5 * 60 * 1000));
		reservation.setEndDate(new Date(System.currentTimeMillis() + 10 * 60 * 1000));
		reservationService.create(reservation);

		reservation = createReservation();
		reservation.setId("2");
		reservation.setState(ReservationState.CANCELLED);
		reservation.setBeginDate(new Date(System.currentTimeMillis() + 5 * 60 * 1000));
		reservation.setEndDate(new Date(System.currentTimeMillis() + 10 * 60 * 1000));
		reservationService.create(reservation);

		reservation = createReservation();
		reservation.setId("3");
		reservation.setState(ReservationState.RESERVED);
		reservation.setAircraftId("2");
		reservation.setBeginDate(new Date(System.currentTimeMillis() + 5 * 60 * 1000));
		reservation.setEndDate(new Date(System.currentTimeMillis() + 10 * 60 * 1000));
		reservationService.create(reservation);

		Reservation result = reservationService.findByAircraftIdAndTimeInterval("1",
				new Date(System.currentTimeMillis() + 4 * 60 * 1000), new Date(
						System.currentTimeMillis() + 15 * 60 * 1000));
		Assert.assertNotNull(result);
	}

	@Test
	@InSequence(2)
	public void testFindByAircraftIdAndTimeInterval5() {

		Reservation reservation = createReservation();
		reservation.setState(ReservationState.RESERVED);
		reservation.setBeginDate(new Date(System.currentTimeMillis() + 5 * 60 * 1000));
		reservation.setEndDate(new Date(System.currentTimeMillis() + 10 * 60 * 1000));
		reservationService.create(reservation);

		reservation = createReservation();
		reservation.setId("2");
		reservation.setState(ReservationState.CANCELLED);
		reservation.setBeginDate(new Date(System.currentTimeMillis() + 5 * 60 * 1000));
		reservation.setEndDate(new Date(System.currentTimeMillis() + 10 * 60 * 1000));
		reservationService.create(reservation);

		reservation = createReservation();
		reservation.setId("3");
		reservation.setState(ReservationState.RESERVED);
		reservation.setAircraftId("2");
		reservation.setBeginDate(new Date(System.currentTimeMillis() + 5 * 60 * 1000));
		reservation.setEndDate(new Date(System.currentTimeMillis() + 10 * 60 * 1000));
		reservationService.create(reservation);

		Reservation result = reservationService.findByAircraftIdAndTimeInterval("1",
				new Date(System.currentTimeMillis() + 7 * 60 * 1000), new Date(
						System.currentTimeMillis() + 8 * 60 * 1000));
		Assert.assertNotNull(result);
	}

	@Test
	@InSequence(3)
	public void testFindByAircraftId() {
		Reservation reservation = createReservation();
		reservationService.create(reservation);

		reservation = createReservation();
		reservation.setId("2");
		reservation.setUserId("2");
		reservationService.create(reservation);

		List<Reservation> result = reservationService.findActiveReservationsByUserId("1");

		Assert.assertEquals(1, result.size());
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

	@After
	public void cleanup() {
		for (Reservation reservation : reservationService.findAll()) {
			reservationService.delete(reservation.getId());
		}
	}
}