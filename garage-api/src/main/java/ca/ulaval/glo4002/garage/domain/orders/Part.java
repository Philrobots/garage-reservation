package ca.ulaval.glo4002.garage.domain.orders;

import javax.persistence.*;


@Entity
@Table(name = "parts")
public class Part {

    private int id;

    private String name;

    private int quantity;

    private int order_id;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Column(name = "quantity")
    public int getQuantity() {
        return quantity;
    }

    @Id
    @Column(name="id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    @Column(name = "order_id")
    public int getOrder_id() {
        return order_id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }
}
