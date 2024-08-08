package com.example.r2dbc.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.Objects;

@Table(name = "orders")
public class Order {
    @Id
    private Long id;
    private Long customerId;

    private double price;
    @CreatedDate
    private Instant createdAt;

    public Order() {
    }

    public Order(Long id, Long customerId, double price, Instant createdAt) {
        this.id = id;
        this.customerId = customerId;
        this.price = price;
        this.createdAt = createdAt;
    }

    public Order(Long id, Long customerId, double price) {
        this.id = id;
        this.customerId = customerId;
        this.price = price;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
