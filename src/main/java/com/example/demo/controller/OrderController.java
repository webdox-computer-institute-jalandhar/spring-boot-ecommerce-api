package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;

import com.example.demo.model.CartItem;
import com.example.demo.model.Order;
import com.example.demo.model.PurchasedItem;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.PurchasedItemRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@CrossOrigin("*")
public class OrderController {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final PurchasedItemRepository purchasedItemRepository;

    @PostMapping("/place")
    @Transactional
    public Order placeOrder() {
        // Fetch all cart items
        List<CartItem> cartItems = cartRepository.findAll();

        // Calculate the total price
        double totalPrice = cartItems.stream()
                .mapToDouble(i -> i.getProduct().getPrice() * i.getQuantity())
                .sum();

        // Create and save the order
        Order order = Order.builder()
                .cartItems(cartItems)
                .totalPrice(totalPrice)
                .build();
        orderRepository.save(order);

       // Create PurchasedItems for each CartItem and save them
    for (CartItem cartItem : cartItems) {
        PurchasedItem purchasedItem = PurchasedItem.builder()
                .product(cartItem.getProduct())
                .quantity(cartItem.getQuantity())
                .order(order)
                .price(cartItem.getProduct().getPrice())
                .build();

        purchasedItemRepository.save(purchasedItem); // Save the purchased item record
    }

    // Clear the cart after placing the order
    cartRepository.deleteAll();
    cartRepository.flush();
    return order;
    }

    @GetMapping
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }
}
