package com.example.orders.threads;

import com.example.orders.processing.Electronics;
import com.example.orders.processing.Order;
import com.example.orders.storage.OrderStorage;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderThreadManagerTest {
    @Test
    void processesOrdersConcurrently() throws Exception {
        OrderStorage storage = new OrderStorage();
        Electronics product = Electronics.builder().name("Планшет").price(200).brand("Brand").build();
        List<Future<?>> futures = new ArrayList<>();
        try (OrderThreadManager manager = new OrderThreadManager(4, storage)) {
            for (int i = 1; i <= 20; i++) {
                futures.add(manager.submit(new Order<>(i, product, 1)));
            }
            for (Future<?> future : futures) {
                future.get();
            }
        }
        assertEquals(20, storage.count());
    }
}
