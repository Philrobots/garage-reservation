package ca.ulaval.glo4002.garage.infrastructure.persistence.hibernate;

import ca.ulaval.glo4002.garage.application.entityManager.EntityManagerController;
import ca.ulaval.glo4002.garage.application.entityManager.EntityManagerService;
import org.glassfish.jersey.internal.util.Producer;

import ca.ulaval.glo4002.garage.domain.DomainTransaction;

import javax.persistence.EntityManager;

public class HibernateDomainTransaction implements DomainTransaction {
	private EntityManagerService entityManagerService = new EntityManagerController();

	@Override
	public <T> T wrapInTransaction(Producer<T> call) {
		// TODO lab create/commit/rollback transaction
		EntityManager entityManager = entityManagerService.createEntityManager();
		entityManager.getTransaction().begin();
		T t = call.call();
		entityManager.getTransaction().commit();
		entityManagerService.closeEntityManager();
		return t;
	}
}
