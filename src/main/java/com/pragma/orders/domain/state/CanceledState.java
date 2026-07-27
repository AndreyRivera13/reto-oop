package com.pragma.orders.domain.state;

import com.pragma.orders.domain.OrderStatus;

public class CanceledState extends OrderState {

    @Override
    public OrderStatus getStatus() {
        return OrderStatus.CANCELED;
    }

    @Override
    public boolean canTransitionTo(OrderStatus target) {
        return false;
    }
}
