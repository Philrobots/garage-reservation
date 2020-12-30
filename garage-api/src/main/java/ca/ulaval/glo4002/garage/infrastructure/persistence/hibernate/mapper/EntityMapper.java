package ca.ulaval.glo4002.garage.infrastructure.persistence.hibernate.mapper;

import ca.ulaval.glo4002.garage.domain.appointments.Appointment;
import ca.ulaval.glo4002.garage.domain.appointments.AppointmentNumber;
import ca.ulaval.glo4002.garage.domain.orders.Order;
import ca.ulaval.glo4002.garage.domain.orders.Part;
import ca.ulaval.glo4002.garage.infrastructure.persistence.hibernate.entity.AppointmentEntity;
import ca.ulaval.glo4002.garage.infrastructure.persistence.hibernate.entity.OrderEntity;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class EntityMapper {

    public AppointmentEntity convertAppointmentToEntity(Appointment appointment) {
        AppointmentEntity appointmentEntity = new AppointmentEntity();
        appointmentEntity.setClientName(appointment.getClientName());
        appointmentEntity.setClientPhone(appointment.getClientPhone());
        appointmentEntity.setAppointmentNumber(appointment.getAppointmentNumber().toString());
        return appointmentEntity;
    }

    public OrderEntity convertOrderToEntity(Order order) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setDate(order.getDate());
        orderEntity.setParts(order.listParts());
        orderEntity.setAppointmentNumber(order.getReferenceNumber().getNumber());
        return orderEntity;
    }

    public Order convertEntityToOrder(OrderEntity orderEntity, List<Part> parts) {
        Order order =  new Order(orderEntity.getDate(), AppointmentNumber.create(orderEntity.getAppointmentNumber()));
        parts.stream().filter(part -> part.getOrder_id() == orderEntity.getId()).forEachOrdered(order::addPart);
        return order;
    }


    public Collection<Appointment> convertEntityToAppointment(Collection<AppointmentEntity> appointmentEntities) {
        return appointmentEntities.stream().map(appointmentEntity -> new Appointment(AppointmentNumber.create(appointmentEntity.getAppointmentNumber()),
                appointmentEntity.getClientName(), appointmentEntity.getClientPhone())).collect(Collectors.toList());
    }
}
