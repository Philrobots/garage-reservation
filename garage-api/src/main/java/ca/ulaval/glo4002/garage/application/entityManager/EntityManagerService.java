package ca.ulaval.glo4002.garage.application.entityManager;

import javax.persistence.EntityManager;

public interface EntityManagerService {
    EntityManager getEntityManger();

    EntityManager createEntityManager();

    void closeEntityManager();

    void initializeEntityManagerFactory();

}
