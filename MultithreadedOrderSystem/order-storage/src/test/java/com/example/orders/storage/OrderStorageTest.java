package com.example.orders.storage;

import com.example.orders.processing.Clothing;
import com.example.orders.processing.InvalidOrderException;
import com.example.orders.processing.Order;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OrderStorageTest {
    @Test
    void preventsDuplicateIds() {
        OrderStorage storage = new OrderStorage();
        Clothing product = Clothing.builder().name("Светр").price(150).size("L").build();
        Order<Clothing> order = new Order<>(1, product, 2);
        storage.save(order);
        assertThrows(InvalidOrderException.class, () -> storage.save(order));
        assertEquals(1, storage.count());
    }
}
