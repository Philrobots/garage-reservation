package ca.ulaval.glo4002.garage.infrastructure.persistence.hibernate.entity;

import javax.persistence.*;

@Entity
@Table(name = "appointments")
public class AppointmentEntity {

    private int id;

    private String clientName;

    private String clientPhone;

    private String appointmentNumber;

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public void setAppointmentNumber(String appointmentNumber) {
        this.appointmentNumber = appointmentNumber;
    }

    @Id
    @Column(name="id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    @Column(name = "appointmentNumber")
    public String getAppointmentNumber() {
        return appointmentNumber;
    }

    @Column(name = "clientName")
    public String getClientName() {
        return clientName;
    }

    @Column(name = "clientPhone")
    public String getClientPhone() {
        return clientPhone;
    }
}
