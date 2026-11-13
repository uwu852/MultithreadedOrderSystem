package com.example.orders.processing;

import lombok.Getter;

@Getter
public abstract class Product {
    private final String name;
    private final double price;

    protected Product(String name, double price) {
        if (name == null || name.isBlank() || !Double.isFinite(price) || price <= 0) {
            throw new InvalidOrderException("Некоректні дані товару");
        }
        this.name = name;
        this.price = price;
    }
}
