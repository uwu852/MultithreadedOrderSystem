package com.example.orders.processing;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OrderProcessorTest {
    @Test
    void builderCreatesElectronics() {
        Electronics product = Electronics.builder().name("Телефон").price(100).brand("Brand").build();
        assertEquals("Телефон", product.getName());
    }

    @Test
    void rejectsInvalidPrice() {
        assertThrows(InvalidOrderException.class,
                () -> Clothing.builder().name("Куртка").price(-1).size("M").build());
    }

    @Test
    void rejectsInvalidQuantity() {
        Electronics product = Electronics.builder().name("Ноутбук").price(500).brand("Brand").build();
        assertThrows(InvalidOrderException.class, () -> new Order<>(1, product, 0));
    }
}
