package com.pragma.orders.domain.state;

import com.pragma.orders.domain.OrderStatus;

public class DeliveredState extends OrderState {

    @Override
    public OrderStatus getStatus() {
        return OrderStatus.DELIVERED;
    }

    @Override
    public boolean canTransitionTo(OrderStatus target) {
        return false;
    }
}
