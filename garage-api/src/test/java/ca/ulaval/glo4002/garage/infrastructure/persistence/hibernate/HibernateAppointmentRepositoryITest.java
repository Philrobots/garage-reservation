package ca.ulaval.glo4002.garage.infrastructure.persistence.hibernate;

import ca.ulaval.glo4002.garage.application.entityManager.EntityManagerService;
import ca.ulaval.glo4002.garage.domain.appointments.Appointment;
import ca.ulaval.glo4002.garage.domain.appointments.AppointmentNumber;
import ca.ulaval.glo4002.garage.domain.appointments.DuplicateAppointmentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Collection;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class HibernateAppointmentRepositoryITest {

    private static final String  A_CLIENT_NAME = "Philippe Vincent";
    private static final String A_PHONE_NUMBER = "582-3556-345";
    private static final String A_APPOINTMENT_NUMBER_STRING = "12345";
    private static final String  ANOTHER_CLIENT_NAME = "Philippe Vincent";
    private static final String ANOTHER_PHONE_NUMBER = "582-3556-345";
    private static final String ANOTHER_APPOINTMENT_NUMBER_STRING = "9876543456";

    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("garage-test");

    @Mock
    private EntityManagerService entityManagerService;

    private EntityManager entityManager;

    private HibernateAppointmentRepository hibernateAppointmentRepository;

    private Appointment appointment;

    private Appointment anotherAppointment;


    @BeforeEach
    public void setupHibernateAppointmentRepo() {
        hibernateAppointmentRepository = new HibernateAppointmentRepository(entityManagerService);
        entityManager = createEntityManager();
        appointment = givenAAppointment();
        anotherAppointment = givenAnotherAppointment();
    }

    @Test
    void saveAppointment_canThenRetrieveItByNumber() {
        // TODO lab
        given(entityManagerService.createEntityManager()).willReturn(entityManager);
        given(entityManagerService.getEntityManger()).willReturn(getEntityManger());

        hibernateAppointmentRepository.save(anotherAppointment);

        // when
        Optional<Appointment> appointment = hibernateAppointmentRepository.findByNumber(anotherAppointment.getAppointmentNumber());

        // then
        assertThat(appointment.get().getAppointmentNumber()).isEqualTo(anotherAppointment.getAppointmentNumber());
    }

    @Test
    void saveAppointment_createsAnErrorWhenAnAppointmentIsSavedTwiceWithTheSameNumber() {
         given(entityManagerService.getEntityManger()).willReturn(getEntityManger());
          hibernateAppointmentRepository.save(appointment);

        // when
         Executable validateAppointmentAreNotDuplicate = () -> hibernateAppointmentRepository.save(appointment);

        // then
         assertThrows(DuplicateAppointmentException.class, validateAppointmentAreNotDuplicate);
    }

    @Test
    void saveTwoAppointments_canRetrieveAListOfAppointments() {
        // TODO lab
        given(entityManagerService.createEntityManager()).willReturn(entityManager);
        given(entityManagerService.getEntityManger()).willReturn(getEntityManger());

        hibernateAppointmentRepository.save(appointment);
        hibernateAppointmentRepository.save(anotherAppointment);

        Collection<Appointment> appointments = hibernateAppointmentRepository.findAll();

        assertThat(appointments.size()).isEqualTo(2);
    }

    @Test
    void findAppointmentByNumber_resultIsEmptyIfAppointmentDoesNotExist() {
        // TODO lab
        given(entityManagerService.createEntityManager()).willReturn(entityManager);

       Optional<Appointment> appointment = hibernateAppointmentRepository.findByNumber(givenAAppointment().getAppointmentNumber());

       assertThat(appointment.isPresent()).isFalse();
    }

    private EntityManager createEntityManager() {
        entityManager = entityManagerFactory.createEntityManager();
        return entityManager;
    }

    private EntityManager getEntityManger() {
        entityManager.getTransaction().begin();
        return entityManager;
    }

    private Appointment givenAnotherAppointment() {
        return new Appointment(AppointmentNumber.create(ANOTHER_APPOINTMENT_NUMBER_STRING), ANOTHER_CLIENT_NAME, ANOTHER_PHONE_NUMBER);
    }

    private Appointment givenAAppointment() {
        return new Appointment(AppointmentNumber.create(A_APPOINTMENT_NUMBER_STRING), A_CLIENT_NAME, A_PHONE_NUMBER);
    }

}