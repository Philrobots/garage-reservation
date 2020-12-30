package ca.ulaval.glo4002.garage.application.entityManager;

import javax.persistence.EntityManager;

public class EntityManagerCreator {

    private static EntityManager entityManager;

    private EntityManagerCreator(){};

    public static EntityManager createEntityManager() {
        entityManager = EntityManagerFactoryCreator.getEntityManagerFactory().createEntityManager();
        return entityManager;
    }

    public static void closeEntityManager() {
        if (entityManager != null) {
            entityManager.close();
            entityManager = null;
        }
    }

    public static EntityManager getEntityManager() {
        if (entityManager == null) {
            return EntityManagerFactoryCreator.getEntityManagerFactory().createEntityManager();
        } else {
            return entityManager;
        }
    }

}
