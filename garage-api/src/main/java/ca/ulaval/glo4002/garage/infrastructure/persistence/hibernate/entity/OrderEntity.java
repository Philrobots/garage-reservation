package ca.ulaval.glo4002.garage.infrastructure.persistence.hibernate.entity;

import ca.ulaval.glo4002.garage.domain.orders.Part;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


@Entity
@Table(name = "orders")
public class OrderEntity {

    private int id;

    private int order_id = new Random().nextInt(1000);

    private LocalDateTime date;

    private String appointmentNumber;

    private List<Part> parts = new ArrayList<>();

    public void setAppointmentNumber(String appointmentNumber) {
        this.appointmentNumber = appointmentNumber;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public void setParts(List<Part> parts) {
        this.parts = parts;
    }

    @OneToMany
    public List<Part> getParts() {
        return parts;
    }

    public int getOrder_id() {
        return order_id;
    }

    @Column(name = "appointmentNumber")
    public String getAppointmentNumber() {
        return appointmentNumber;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", unique = true, nullable = false)
    public int getId() {
        return id;
    }

    @Column(name = "date")
    public LocalDateTime getDate() {
        return date;
    }
}
