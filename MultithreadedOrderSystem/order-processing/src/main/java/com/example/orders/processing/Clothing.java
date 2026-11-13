package com.example.orders.processing;

import lombok.Builder;
import lombok.Getter;

@Getter
public final class Clothing extends Product {
    private final String size;

    @Builder
    public Clothing(String name, double price, String size) {
        super(name, price);
        if (size == null || size.isBlank()) {
            throw new InvalidOrderException("Вкажіть розмір одягу");
        }
        this.size = size;
    }
}
