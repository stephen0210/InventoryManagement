package org.inventory.controller;

import org.inventory.entity.Order;
import org.inventory.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    public Order placeOrder(@RequestParam Long partId, @RequestParam int quantity) {
        return orderService.placeOrder(partId, quantity);
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }
}