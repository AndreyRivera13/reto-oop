package com.pragma.orders.application;

import com.pragma.orders.domain.Order;
import com.pragma.orders.domain.Product;
import com.pragma.orders.infrastructure.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @MockBean
    private OrderRepository orderRepository;

    @Test
    void createOrderTest() {
        Product product = new Product(1L, "Product 1", 10.0);
        List<Product> products = List.of(product);
        Order order = orderService.createOrder("Customer 1", products);
        assertNotNull(order);
        assertEquals("Customer 1", order.getCustomer());
        assertEquals(1, order.getProducts().size());
    }

    @Test
    void updateOrderTest() {
        Product product = new Product(1L, "Product 1", 10.0);
        List<Product> products = List.of(product);
        Order order = new Order(1L, "Customer 1", products, LocalDate.now(), OrderStatus.PENDING);
        when(orderRepository.findById(1L)).thenReturn(java.util.Optional.of(order));
        Order updatedOrder = orderService.updateOrder(1L, "Customer 2", products);
        assertEquals("Customer 2", updatedOrder.getCustomer());
    }

    @Test
    void deleteOrderTest() {
        orderService.deleteOrder(1L);
        verify(orderRepository, times(1)).deleteById(1L);
    }

    @Test
    void changeOrderStatusTest() {
        Product product = new Product(1L, "Product 1", 10.0);
        List<Product> products = List.of(product);
        Order order = new Order(1L, "Customer 1", products, LocalDate.now(), OrderStatus.PENDING);
        when(orderRepository.findById(1L)).thenReturn(java.util.Optional.of(order));
        Order updatedOrder = orderService.changeOrderStatus(1L, OrderStatus.DELIVERED);
        assertEquals(OrderStatus.DELIVERED, updatedOrder.getStatus());
    }
}