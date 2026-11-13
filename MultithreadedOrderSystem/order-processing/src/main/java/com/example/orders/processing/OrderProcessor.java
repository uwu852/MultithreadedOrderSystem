package com.example.orders.processing;

import com.example.orders.processing.internal.OrderValidation;

public final class OrderProcessor<T extends Product> {
    public void process(Order<T> order) {
        OrderValidation.check(order);
        System.out.printf("Потік %s обробляє замовлення №%d: %s, кількість %d%n",
                Thread.currentThread().getName(), order.getId(),
                order.getProduct().getName(), order.getQuantity());
    }
}
