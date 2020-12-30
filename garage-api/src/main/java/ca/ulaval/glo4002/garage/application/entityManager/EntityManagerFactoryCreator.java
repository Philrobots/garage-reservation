package ca.ulaval.glo4002.garage.application.entityManager;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactoryCreator {

    private static EntityManagerFactory entityManagerFactory;

    private EntityManagerFactoryCreator(){};

    public static void initializeEntityManagerFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("garage");
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        if (entityManagerFactory == null) {
            initializeEntityManagerFactory();
        }
        return entityManagerFactory;
    }

}
