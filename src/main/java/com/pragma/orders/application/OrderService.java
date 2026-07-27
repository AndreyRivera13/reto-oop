package com.pragma.orders.application;
import com.pragma.orders.domain.Order;
import com.pragma.orders.domain.OrderStatus;
import com.pragma.orders.domain.Product;
import com.pragma.orders.infrastructure.OrderRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    public Order createOrder(String customer, List<Product> products) {
        Order order = new Order(null, customer, products, LocalDate.now(), OrderStatus.PENDING);
        return orderRepository.save(order);
    }
    public Order updateOrder(Long id, String customer, List<Product> products) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setCustomer(customer);
        order.setProducts(products);
        order.setDate(LocalDate.now());
        return orderRepository.save(order);
    }
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
    public Order changeOrderStatus(Long id, OrderStatus status) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        return orderRepository.save(order);
    }
}
