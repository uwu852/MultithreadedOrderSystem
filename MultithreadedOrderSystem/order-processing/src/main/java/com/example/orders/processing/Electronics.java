package com.example.orders.processing;

import lombok.Builder;
import lombok.Getter;

@Getter
public final class Electronics extends Product {
    private final String brand;

    @Builder
    public Electronics(String name, double price, String brand) {
        super(name, price);
        if (brand == null || brand.isBlank()) {
            throw new InvalidOrderException("Вкажіть виробника електроніки");
        }
        this.brand = brand;
    }
}
