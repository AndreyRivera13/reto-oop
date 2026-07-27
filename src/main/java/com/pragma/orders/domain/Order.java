package com.pragma.orders.domain;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import com.pragma.orders.domain.state.OrderState;
import com.pragma.orders.domain.state.OrderStateFactory;
import java.time.LocalDate;
import java.util.List;
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customer;
    @ManyToMany
    @JoinTable(
        name = "order_products",
        joinColumns = @JoinColumn(name = "order_id"),
        inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products;
    private LocalDate date;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    protected Order() {
    }
    public Order(Long id, String customer, List<Product> products, LocalDate date, OrderStatus status) {
        this.id = id;
        setCustomer(customer);
        setProducts(products);
        setDate(date);
        setStatus(status);
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
        if (customer == null || customer.isBlank()) {
            throw new IllegalArgumentException("El cliente del pedido no puede estar vacio");
        }
        this.customer = customer;
    }
    public List<Product> getProducts() {
        return products;
    }
    public void setProducts(List<Product> products) {
        if (products == null || products.isEmpty()) {
            throw new IllegalArgumentException("El pedido debe tener al menos un producto");
        }
        this.products = products;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("La fecha del pedido no puede ser nula");
        }
        if (date.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha del pedido no puede ser futura");
        }
        this.date = date;
    }
    public OrderStatus getStatus() {
        return status;
    }
    public void setStatus(OrderStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("El estado del pedido no puede ser nulo");
        }
        this.status = status;
    }

    /**
     * Cambia el estado del pedido validando que la transicion sea permitida
     * segun el estado actual (patron State, resuelto via OrderStateFactory).
     */
    public void changeStatus(OrderStatus newStatus) {
        if (newStatus == null) {
            throw new IllegalArgumentException("El nuevo estado del pedido no puede ser nulo");
        }
        OrderState currentState = OrderStateFactory.forStatus(this.status);
        currentState.validateTransitionTo(newStatus);
        this.status = newStatus;
    }

    /**
     * Agrega un producto al pedido. Solo se permite mientras el pedido
     * este en un estado que admita modificaciones (PENDING).
     */
    public void addProduct(Product product) {
        ensureModifiable();
        if (product == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo");
        }
        if (products.contains(product)) {
            throw new IllegalArgumentException("El producto ya esta incluido en el pedido");
        }
        products.add(product);
    }

    /**
     * Elimina un producto del pedido. Solo se permite mientras el pedido
     * este en un estado que admita modificaciones (PENDING), y el pedido
     * debe conservar al menos un producto.
     */
    public void removeProduct(Product product) {
        ensureModifiable();
        if (product == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo");
        }
        if (products.size() <= 1) {
            throw new IllegalStateException("El pedido debe conservar al menos un producto");
        }
        boolean removed = products.remove(product);
        if (!removed) {
            throw new IllegalArgumentException("El producto no pertenece al pedido");
        }
    }

    private void ensureModifiable() {
        OrderState currentState = OrderStateFactory.forStatus(this.status);
        if (!currentState.allowsModification()) {
            throw new IllegalStateException(
                "No se pueden modificar los productos de un pedido en estado " + this.status);
        }
    }
}
