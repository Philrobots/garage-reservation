package ca.ulaval.glo4002.garage.infrastructure.persistence.hibernate;

import ca.ulaval.glo4002.garage.application.entityManager.EntityManagerService;
import ca.ulaval.glo4002.garage.domain.appointments.AppointmentNumber;
import ca.ulaval.glo4002.garage.domain.orders.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class HibernateOrderRepositoryITest {

    private final static LocalDateTime A_DATE = LocalDateTime.now();
    private final static String A_APPOINTMENT_NUMBER = "@#$%FKKS";
    private final static String ANOTHER_APPOINTMENT_NUMBER = "23423er";

    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("garage-test");

    @Mock
    private EntityManagerService entityManagerService;

    private Order aOrder;

    private Order anotherOrder;

    private EntityManager entityManager;

    private HibernateOrderRepository hibernateOrderRepository;

    @BeforeEach
    public void setupHibernateOrderRepo() {
        hibernateOrderRepository = new HibernateOrderRepository(entityManagerService);
        entityManager = createEntityManager();
        aOrder = new Order(A_DATE, AppointmentNumber.create(A_APPOINTMENT_NUMBER));
        anotherOrder = new Order(A_DATE, AppointmentNumber.create(ANOTHER_APPOINTMENT_NUMBER));
    }

    @Test
    void findAll_returnsAllSavedOrders() {
        // TODO lab
         given(entityManagerService.createEntityManager()).willReturn(entityManager);
         given(entityManagerService.getEntityManger()).willReturn(getEntityManager());
         hibernateOrderRepository.save(aOrder);

         List<Order> orders =  hibernateOrderRepository.findAll();

         assertThat(orders.get(0).getReferenceNumber()).isEqualTo(aOrder.getReferenceNumber());
    }

    private EntityManager getEntityManager() {
        entityManager.getTransaction().begin();
        return entityManager;
    }

    private EntityManager createEntityManager() {
        entityManager =  entityManagerFactory.createEntityManager();
        return entityManager;
    }

}