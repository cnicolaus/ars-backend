package com.prodyna.pac.ars.backend.common.producer;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class EntityManagerProducer {

	@Produces
	@PersistenceContext(unitName = "arsPU")
	private EntityManager entityManager;
}
