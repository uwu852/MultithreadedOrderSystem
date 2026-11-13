package com.example.orders.threads;

import com.example.orders.processing.Order;
import com.example.orders.processing.OrderProcessor;
import com.example.orders.processing.Product;
import com.example.orders.storage.OrderStorage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public final class OrderThreadManager implements AutoCloseable {
    private final ExecutorService executor;
    private final OrderStorage storage;

    public OrderThreadManager(int threadCount, OrderStorage storage) {
        if (threadCount <= 0 || storage == null) {
            throw new IllegalArgumentException("Некоректні параметри потоків");
        }
        this.executor = Executors.newFixedThreadPool(threadCount);
        this.storage = storage;
    }

    public <T extends Product> Future<?> submit(Order<T> order) {
        OrderProcessor<T> processor = new OrderProcessor<>();
        Consumer<Order<T>> action = processor::process;
        return executor.submit(() -> {
            action.accept(order);
            storage.save(order);
        });
    }

    @Override
    public void close() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(30, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
