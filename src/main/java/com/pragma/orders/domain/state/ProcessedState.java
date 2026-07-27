package com.pragma.orders.domain.state;

import com.pragma.orders.domain.OrderStatus;

public class ProcessedState extends OrderState {

    @Override
    public OrderStatus getStatus() {
        return OrderStatus.PROCESSED;
    }

    @Override
    public boolean canTransitionTo(OrderStatus target) {
        return target == OrderStatus.DELIVERED || target == OrderStatus.CANCELED;
    }
}
