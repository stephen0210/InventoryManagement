package org.inventory.service;

import org.inventory.entity.Order;
import org.inventory.entity.Part;
import org.inventory.entity.Supplier;
import org.inventory.repository.OrderRepository;
import org.inventory.repository.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private PartRepository partRepository;

    @Retryable(value = {RuntimeException.class}, maxAttempts = 3, backoff = @Backoff(delay = 5000))

    public Order placeOrder(Long partId, int quantity) {
        Part part = partRepository.findById(partId)
                .orElseThrow(() -> new RuntimeException("Part not found"));

        Order order = new Order();
        order.setPart(part);
        order.setQuantity(quantity);
        order.setSupplier(part.getSupplier());
        order.setOrderTime(LocalDateTime.now());
        return orderRepository.save(order);
    }

    @Recover
    public void recover(RuntimeException e) {
        // Log or store failed order attempt for future retries
        System.out.println("Order failed after retries: " + e.getMessage());
    }

    @Scheduled(cron = "0 0 0-1 * * ?") // Every hour between 12 AM and 1 AM
    public void manageInventoryAndOrders() {
        List<Part> parts = partRepository.findAll();

        for (Part part : parts) {
            if (part.getAvailableQty() < part.getThresholdLimit()) {
                if (part.getSupplier() == Supplier.SUPPLIER_A) {
                    placeOrder(part.getId(), part.getMinOrderQty());
                } else if (part.getSupplier() == Supplier.SUPPLIER_B) {
                    placeOrder(part.getId(), part.getMinOrderQty());
                }
            }
        }

        //sendEmailAlert();
        //sendSMSAlert();
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
