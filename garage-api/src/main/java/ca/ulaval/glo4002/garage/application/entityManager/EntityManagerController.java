package ca.ulaval.glo4002.garage.application.entityManager;

import javax.persistence.EntityManager;

public class EntityManagerController implements EntityManagerService {

    public EntityManager getEntityManger() {
        return EntityManagerCreator.getEntityManager();
    }

    public EntityManager createEntityManager() {
        return EntityManagerCreator.createEntityManager();
    }

    public void closeEntityManager() {
        EntityManagerCreator.closeEntityManager();
    }

    @Override
    public void initializeEntityManagerFactory() {
     EntityManagerFactoryCreator.initializeEntityManagerFactory();
    }
}
