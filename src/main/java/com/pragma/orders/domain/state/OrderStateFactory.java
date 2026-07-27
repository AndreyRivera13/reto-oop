package com.pragma.orders.domain.state;

import com.pragma.orders.domain.OrderStatus;

/**
 * Fabrica que resuelve la instancia de OrderState concreta a partir
 * del OrderStatus persistido en el pedido (patrón Factory).
 */
public final class OrderStateFactory {

    private OrderStateFactory() {
    }

    public static OrderState forStatus(OrderStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("El estado del pedido no puede ser nulo");
        }
        switch (status) {
            case PENDING:
                return new PendingState();
            case PROCESSED:
                return new ProcessedState();
            case DELIVERED:
                return new DeliveredState();
            case CANCELED:
                return new CanceledState();
            default:
                throw new IllegalArgumentException("Estado de pedido no soportado: " + status);
        }
    }
}
