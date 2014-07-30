package com.prodyna.pac.ars.backend.common.producer;

import java.lang.management.ManagementFactory;

import javax.enterprise.inject.Produces;
import javax.management.MBeanServer;

public class MBeanServerProducer {

	@Produces
	public MBeanServer create() {
		return ManagementFactory.getPlatformMBeanServer();
	}
}
