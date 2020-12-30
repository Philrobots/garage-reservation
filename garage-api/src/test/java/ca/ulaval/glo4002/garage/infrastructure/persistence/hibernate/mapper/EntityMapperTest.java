package ca.ulaval.glo4002.garage.infrastructure.persistence.hibernate.mapper;

import ca.ulaval.glo4002.garage.domain.appointments.Appointment;
import ca.ulaval.glo4002.garage.domain.orders.Order;
import ca.ulaval.glo4002.garage.domain.orders.Part;
import ca.ulaval.glo4002.garage.infrastructure.persistence.hibernate.entity.AppointmentEntity;
import ca.ulaval.glo4002.garage.domain.appointments.AppointmentNumber;
import ca.ulaval.glo4002.garage.infrastructure.persistence.hibernate.entity.OrderEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class EntityMapperTest {

    private static final String  A_CLIENT_NAME = "Philippe Vincent";
    private static final String A_PHONE_NUMBER = "582-3556-345";
    private static final int A_QUANTITY = 10;
    private static final int A__ID = 432345;
    private static final String A_PART_NAME = "WHAT IT DO BABY";
    private static final String A_APPOINTMENT_NUMBER_STRING = "12345";
    private static final LocalDateTime A_DATE = LocalDateTime.now();

    private AppointmentNumber appointmentNumber;

    private EntityMapper entityMapper;

    @BeforeEach
    void setUpEntityMapper() {
        entityMapper = new EntityMapper();
        appointmentNumber = AppointmentNumber.create(A_APPOINTMENT_NUMBER_STRING);
    }


    @Test
    public void givenAnAppointment_whenConvertAppointmentToEntity_thenAppointmentShouldHaveTheSameArgument() {
        // given
        Appointment appointment = new Appointment(appointmentNumber, A_CLIENT_NAME, A_PHONE_NUMBER);

        // when
        AppointmentEntity appointmentEntity = entityMapper.convertAppointmentToEntity(appointment);

        // then
        assertThat(appointmentEntity.getClientPhone()).isEqualTo(appointment.getClientPhone());
        assertThat(appointmentEntity.getAppointmentNumber()).isEqualTo(appointment.getAppointmentNumber().toString());
        assertThat(appointmentEntity.getClientName()).isEqualTo(appointment.getClientName());

    }

    @Test
    public void givenAnOrder_whenConvertOrderToEntity_thenOrderShouldHaveTheSameArgument() {
        // given
        Order order = new Order(A_DATE, appointmentNumber);

        // when
        OrderEntity orderEntity = entityMapper.convertOrderToEntity(order);

        // then
        assertThat(orderEntity.getDate()).isEqualTo(order.getDate());
        assertThat(orderEntity.getAppointmentNumber()).isEqualTo(order.getReferenceNumber().getNumber());

    }

    @Test
    public void givenAOrderEntity_whenConvertEntityToOrder_thenOrderShouldHaveTheSameArgumentAsEntity() {
        // given
        List<Part> parts = new ArrayList<>();
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setAppointmentNumber(appointmentNumber.toString());
        orderEntity.setDate(A_DATE);
        orderEntity.setId(A__ID);
        orderEntity.setParts(parts);

        // when
        Order order = entityMapper.convertEntityToOrder(orderEntity, parts);

        //then
        assertThat(order.listParts()).isEqualTo(orderEntity.getParts());
        assertThat(order.getDate()).isEqualTo(orderEntity.getDate());
        assertThat(order.getReferenceNumber().toString()).isEqualTo(orderEntity.getAppointmentNumber());
    }
}
