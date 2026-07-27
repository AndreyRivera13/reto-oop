package com.pragma.orders.domain.state;

import com.pragma.orders.domain.OrderStatus;

public abstract class OrderState {

    public abstract OrderStatus getStatus();

    public abstract boolean canTransitionTo(OrderStatus target);

    public void validateTransitionTo(OrderStatus target) {
        if (target == getStatus()) {
            return;
        }
        if (!canTransitionTo(target)) {
            throw new IllegalStateException(
                "Transicion de estado invalida: " + getStatus() + " -> " + target);
        }
    }

    public boolean allowsModification() {
        return false;
    }
}
