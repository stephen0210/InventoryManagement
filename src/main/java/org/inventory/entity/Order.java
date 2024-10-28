package org.inventory.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "part_id")
    private Part part;

    private int quantity;

    @Enumerated(EnumType.STRING)
    private Supplier supplier;
    private LocalDateTime orderTime;


    public void setId(Long id) {
        this.id = id;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public Long getId() {
        return id;
    }

    public Part getPart() {
        return part;
    }

    public int getQuantity() {
        return quantity;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }
}
