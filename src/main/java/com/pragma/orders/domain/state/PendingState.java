package com.pragma.orders.domain.state;

import com.pragma.orders.domain.OrderStatus;

public class PendingState extends OrderState {

    @Override
    public OrderStatus getStatus() {
        return OrderStatus.PENDING;
    }

    @Override
    public boolean canTransitionTo(OrderStatus target) {
        return target == OrderStatus.PROCESSED || target == OrderStatus.CANCELED;
    }

    @Override
    public boolean allowsModification() {
        return true;
    }
}
