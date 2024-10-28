package org.inventory.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "parts")
public class Part {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Name must not be blank")
    private String name;
    @Min(value = 0, message = "thresholdLimit quantity must be non-negative")

    private int thresholdLimit;
    @Min(value = 0, message = "Available quantity must be non-negative")

    private int availableQty;
    @NotNull(message = "Supplier must not be null")
    @Pattern(regexp = "SUPPLIER_A|SUPPLIER_B", message = "Supplier must be either SUPPLIER_A or SUPPLIER_B")
    @Enumerated(EnumType.STRING)
    private Supplier supplier;

    @Min(value = 1, message = "Minimum order quantity must be positive")

    private int minOrderQty;


    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setThresholdLimit(int thresholdLimit) {
        this.thresholdLimit = thresholdLimit;
    }

    public void setAvailableQty(int availableQty) {
        this.availableQty = availableQty;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public void setMinOrderQty(int minOrderQty) {
        this.minOrderQty = minOrderQty;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getThresholdLimit() {
        return thresholdLimit;
    }

    public int getAvailableQty() {
        return availableQty;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public int getMinOrderQty() {
        return minOrderQty;
    }
}

