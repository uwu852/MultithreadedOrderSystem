package com.example.orders.storage;

import com.example.orders.processing.InvalidOrderException;
import com.example.orders.processing.Order;
import com.example.orders.processing.Product;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public final class OrderStorage {
    private final ConcurrentMap<Long, Order<? extends Product>> orders = new ConcurrentHashMap<>();

    public void save(Order<? extends Product> order) {
        if (order == null) {
            throw new InvalidOrderException("Замовлення відсутнє");
        }
        if (orders.putIfAbsent(order.getId(), order) != null) {
            throw new InvalidOrderException("Замовлення з таким ID вже існує: " + order.getId());
        }
    }

    public List<Order<? extends Product>> findAll() {
        return orders.values().stream()
                .sorted(java.util.Comparator.comparingLong(Order::getId))
                .toList();
    }

    public int count() {
        return orders.size();
    }
}
