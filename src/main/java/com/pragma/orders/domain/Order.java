package com.pragma.orders.domain;

import java.time.LocalDate;
import java.util.List;

public class Order {
    private Long id;
    private String customer;
    private List<Product> products;
    private LocalDate date;
    private OrderStatus status;

    public Order(Long id, String customer, List<Product> products, LocalDate date, OrderStatus status) {
        this.id = id;
        this.customer = customer;
        this.products = products;
        this.date = date;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}