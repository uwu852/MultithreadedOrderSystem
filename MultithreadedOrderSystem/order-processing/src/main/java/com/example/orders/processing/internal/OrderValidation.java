package com.example.orders.processing.internal;

import com.example.orders.processing.InvalidOrderException;
import com.example.orders.processing.Order;
import com.example.orders.processing.Product;

public final class OrderValidation {
    private OrderValidation() {
    }

    public static void check(Order<? extends Product> order) {
        if (order == null || order.getProduct() == null || order.getQuantity() <= 0) {
            throw new InvalidOrderException("Неможливо обробити замовлення");
        }
    }
}
