package com.pragma.orders.application;
import com.pragma.orders.domain.Order;
import com.pragma.orders.domain.OrderStatus;
import com.pragma.orders.domain.Product;
import com.pragma.orders.infrastructure.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.time.LocalDate;
import java.util.ArrayList;
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
        Order order = new Order(1L, "Customer 1", products, LocalDate.now(), OrderStatus.PROCESSED);
        when(orderRepository.findById(1L)).thenReturn(java.util.Optional.of(order));
        Order updatedOrder = orderService.changeOrderStatus(1L, OrderStatus.DELIVERED);
        assertEquals(OrderStatus.DELIVERED, updatedOrder.getStatus());
    }
    @Test
    void changeOrderStatusInvalidTransitionTest() {
        Product product = new Product(1L, "Product 1", 10.0);
        List<Product> products = List.of(product);
        Order order = new Order(1L, "Customer 1", products, LocalDate.now(), OrderStatus.PENDING);
        when(orderRepository.findById(1L)).thenReturn(java.util.Optional.of(order));
        assertThrows(IllegalStateException.class,
            () -> orderService.changeOrderStatus(1L, OrderStatus.DELIVERED));
    }
    @Test
    void addProductToPendingOrderTest() {
        Product product1 = new Product(1L, "Product 1", 10.0);
        Product product2 = new Product(2L, "Product 2", 20.0);
        List<Product> products = new ArrayList<>(List.of(product1));
        Order order = new Order(1L, "Customer 1", products, LocalDate.now(), OrderStatus.PENDING);
        when(orderRepository.findById(1L)).thenReturn(java.util.Optional.of(order));
        Order updatedOrder = orderService.addProductToOrder(1L, product2);
        assertEquals(2, updatedOrder.getProducts().size());
    }
    @Test
    void removeProductFromPendingOrderTest() {
        Product product1 = new Product(1L, "Product 1", 10.0);
        Product product2 = new Product(2L, "Product 2", 20.0);
        List<Product> products = new ArrayList<>(List.of(product1, product2));
        Order order = new Order(1L, "Customer 1", products, LocalDate.now(), OrderStatus.PENDING);
        when(orderRepository.findById(1L)).thenReturn(java.util.Optional.of(order));
        Order updatedOrder = orderService.removeProductFromOrder(1L, product2);
        assertEquals(1, updatedOrder.getProducts().size());
    }
    @Test
    void cannotModifyProductsOfNonPendingOrderTest() {
        Product product1 = new Product(1L, "Product 1", 10.0);
        Product product2 = new Product(2L, "Product 2", 20.0);
        List<Product> products = new ArrayList<>(List.of(product1));
        Order order = new Order(1L, "Customer 1", products, LocalDate.now(), OrderStatus.DELIVERED);
        when(orderRepository.findById(1L)).thenReturn(java.util.Optional.of(order));
        assertThrows(IllegalStateException.class,
            () -> orderService.addProductToOrder(1L, product2));
    }
}
