package ca.ulaval.glo4002.garage.infrastructure.persistence.hibernate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import ca.ulaval.glo4002.garage.application.entityManager.EntityManagerController;
import ca.ulaval.glo4002.garage.application.entityManager.EntityManagerService;
import ca.ulaval.glo4002.garage.domain.appointments.Appointment;
import ca.ulaval.glo4002.garage.domain.appointments.DuplicateAppointmentException;
import ca.ulaval.glo4002.garage.infrastructure.persistence.hibernate.entity.AppointmentEntity;
import ca.ulaval.glo4002.garage.domain.appointments.AppointmentNumber;
import ca.ulaval.glo4002.garage.domain.appointments.AppointmentRepository;
import ca.ulaval.glo4002.garage.infrastructure.persistence.hibernate.mapper.EntityMapper;

import javax.persistence.EntityManager;

public class HibernateAppointmentRepository implements AppointmentRepository {

    private final EntityMapper entityMapper = new EntityMapper();
    private EntityManagerService entityManagerService;

    public HibernateAppointmentRepository() {
        entityManagerService = new EntityManagerController();
    };

    public HibernateAppointmentRepository(EntityManagerService entityManagerService) {
        this.entityManagerService = entityManagerService;
    }

    @Override
    public void save(Appointment appointment) {
        // TODO lab. Ne pas oublier de lancer DuplicateAppointment si le numéro existe déjà.
            EntityManager entityManager = entityManagerService.getEntityManger();

            if (isAppointmentNumberAlreadyUser(appointment.getAppointmentNumber().getNumber(), entityManager)) {
                throw new DuplicateAppointmentException();
            }

            try {
                AppointmentEntity appointmentEntity = entityMapper.convertAppointmentToEntity(appointment);
                entityManager.persist(appointmentEntity);
            } catch (Exception e) {
                System.out.print(e);
            }
    }

    private boolean isAppointmentNumberAlreadyUser(String appointment, EntityManager entityManager) {
        List<AppointmentEntity> appointmentEntities = entityManager.createQuery("select appointments from AppointmentEntity appointments where appointments.appointmentNumber = :appointmentNumbers").setParameter("appointmentNumbers", appointment).getResultList();
        return !appointmentEntities.isEmpty();
    }

    @Override
    public Collection<Appointment> findAll() {
        // TODO lab
        EntityManager entityManager = entityManagerService.createEntityManager();
        try {
            Collection<AppointmentEntity> appointmentEntities = entityManager.createQuery("select appointments from AppointmentEntity appointments").getResultList();
            Collection appointmentCollection = entityMapper.convertEntityToAppointment(appointmentEntities);
            return appointmentCollection;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public Optional<Appointment> findByNumber(AppointmentNumber appointmentNumbers) {
        // TODO lab
        EntityManager entityManager = entityManagerService.createEntityManager();
        try {
            AppointmentEntity appointmentEntitiy = (AppointmentEntity) entityManager.createQuery("select appointments from AppointmentEntity appointments where appointments.appointmentNumber = :appointmentNumbers").setParameter("appointmentNumbers", appointmentNumbers.toString()).getSingleResult();
            Appointment appointment = new Appointment(AppointmentNumber.create(appointmentEntitiy.getAppointmentNumber()), appointmentEntitiy.getClientName(),
                    appointmentEntitiy.getClientPhone());
            return Optional.ofNullable(appointment);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
