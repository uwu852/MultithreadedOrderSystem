package com.example.orders.processing;

import lombok.Getter;

@Getter
public final class Order<T extends Product> {
    private final long id;
    private final T product;
    private final int quantity;

    public Order(long id, T product, int quantity) {
        if (id <= 0 || product == null || quantity <= 0) {
            throw new InvalidOrderException("Некоректне замовлення");
        }
        this.id = id;
        this.product = product;
        this.quantity = quantity;
    }

    public double getTotal() {
        return product.getPrice() * quantity;
    }
}
