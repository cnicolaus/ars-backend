package com.prodyna.pac.ars.backend.reservation.service;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

import com.prodyna.pac.ars.backend.air.service.Aircraft;
import com.prodyna.pac.ars.backend.common.client.AbstractEntity;
import com.prodyna.pac.ars.backend.common.client.UserRole;
import com.prodyna.pac.ars.backend.common.client.UserRoleNames;
import com.prodyna.pac.ars.backend.common.client.exception.ArsBusinessException;
import com.prodyna.pac.ars.backend.common.client.exception.ArsDuplicateEntityException;
import com.prodyna.pac.ars.backend.common.client.exception.ArsRuntimeException;
import com.prodyna.pac.ars.backend.common.exception.ExceptionExtractor;
import com.prodyna.pac.ars.backend.common.producer.EntityManagerProducer;
import com.prodyna.pac.ars.backend.common.producer.LoggingProducer;
import com.prodyna.pac.ars.backend.common.test.AbstractDataLoader;
import com.prodyna.pac.ars.backend.reservation.service.exception.ArsAircraftNotAvailableException;
import com.prodyna.pac.ars.backend.reservation.service.exception.ArsInvalidStateException;
import com.prodyna.pac.ars.backend.reservation.service.exception.ArsLicenceExpiredException;
import com.prodyna.pac.ars.backend.reservation.service.mapping.ReservationMapper;
import com.prodyna.pac.ars.backend.reservation.service.rest.ArsAircraftNotAvailableExceptionMapper;
import com.prodyna.pac.ars.backend.reservation.service.validation.ReservationValidator;
import com.prodyna.pac.ars.backend.user.service.User;

public class ArchiveCreator {

	public static JavaArchive createArchive() {
		return ShrinkWrap.create(JavaArchive.class).addClass(AbstractDataLoader.class)
				.addClass(ExceptionExtractor.class).addClass(UserRole.class).addClass(AbstractEntity.class)
				.addClass(UserRoleNames.class).addClass(EntityManagerProducer.class)
				.addClass(ArsRuntimeException.class).addClass(ArsDuplicateEntityException.class)
				.addClass(LoggingProducer.class).addClass(ArsAircraftNotAvailableException.class)
				.addClass(ArsInvalidStateException.class).addClass(ArsLicenceExpiredException.class)
				.addClass(ArsBusinessException.class).addPackage(User.class.getPackage())
				.addPackage(Aircraft.class.getPackage())
				.addPackage(ArsAircraftNotAvailableExceptionMapper.class.getPackage())
				.addPackage(ReservationValidator.class.getPackage()).addPackage(ReservationMapper.class.getPackage())
				.addPackage(Reservation.class.getPackage())
				.addAsResource("persistence-test.xml", "META-INF/persistence.xml")
				.addAsResource("beans-test.xml", "META-INF/beans.xml");
	}
}
