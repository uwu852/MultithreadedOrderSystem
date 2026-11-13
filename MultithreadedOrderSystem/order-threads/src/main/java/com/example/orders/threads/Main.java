package com.example.orders.threads;

import com.example.orders.processing.Clothing;
import com.example.orders.processing.Electronics;
import com.example.orders.processing.Order;
import com.example.orders.processing.Product;
import com.example.orders.storage.OrderStorage;
import com.github.javafaker.Faker;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public final class Main {
    public static void main(String[] args) {
        Faker faker = new Faker();
        OrderStorage storage = new OrderStorage();
        List<Future<?>> tasks = new ArrayList<>();

        try (OrderThreadManager manager = new OrderThreadManager(4, storage)) {
            for (int i = 1; i <= 10; i++) {
                if (i % 2 == 0) {
                    Electronics electronics = Electronics.builder()
                            .name(faker.commerce().productName())
                            .price(faker.number().randomDouble(2, 100, 3000))
                            .brand(faker.company().name())
                            .build();
                    tasks.add(manager.submit(new Order<>(i, electronics, 1 + i % 3)));
                } else {
                    Clothing clothing = Clothing.builder()
                            .name(faker.commerce().productName())
                            .price(faker.number().randomDouble(2, 20, 500))
                            .size(faker.options().option("S", "M", "L", "XL"))
                            .build();
                    tasks.add(manager.submit(new Order<>(i, clothing, 1 + i % 3)));
                }
            }
            for (Future<?> task : tasks) {
                task.get();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Обробку перервано");
            return;
        } catch (ExecutionException e) {
            System.err.println("Помилка замовлення: " + e.getCause().getMessage());
            return;
        }

        System.out.println("Збережено замовлень: " + storage.count());
        storage.findAll().forEach(order ->
                System.out.printf("№%d | %s | %.2f грн%n", order.getId(),
                        order.getProduct().getName(), order.getTotal()));

        long electronicsCount = storage.findAll().stream()
                .filter(order -> order.getProduct() instanceof Electronics)
                .count();
        long clothingCount = storage.findAll().stream()
                .filter(order -> order.getProduct() instanceof Clothing)
                .count();
        System.out.println("Електроніка: " + electronicsCount);
        System.out.println("Одяг: " + clothingCount);
    }
}
