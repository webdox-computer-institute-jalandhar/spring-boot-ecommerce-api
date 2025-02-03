package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;

import com.example.demo.model.CartItem;
import com.example.demo.model.Product;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CartController {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    @GetMapping
    public List<CartItem> getCartItems() {
    return cartRepository.findAll().stream()
            .filter(cartItem -> cartItem.getOrder() == null)
            .collect(Collectors.toList());    
        }

    @PostMapping("/add/{productId}")
    public CartItem addToCart(@PathVariable Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Optional<CartItem> existingCartItem = cartRepository.findByProduct(product);

        CartItem cartItem;
        if (existingCartItem.isPresent()) {
            cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + 1);
        } else {
            cartItem = CartItem.builder().product(product).quantity(1).build();
        }

        return cartRepository.save(cartItem);
    }

    @DeleteMapping("/remove/{cartItemId}")
    public void removeFromCart(@PathVariable Long cartItemId) {
        cartRepository.deleteById(cartItemId);
    }

    @DeleteMapping("/clear")
    public void clearCart() {
        cartRepository.deleteAll();
    }

    @PutMapping("/increase/{productId}")
    public CartItem increaseCartItem(@PathVariable Long productId) {
        CartItem cartItem = cartRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found in cart"));

        // Increase the quantity
        cartItem.setQuantity(cartItem.getQuantity() + 1);

        // Save the updated cart item
        return cartRepository.save(cartItem);
    }

    @PutMapping("/decrease/{productId}")
    public CartItem decreaseCartItem(@PathVariable Long productId) {
        CartItem cartItem = cartRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found in cart"));

        if (cartItem.getQuantity() > 1) {
            cartItem.setQuantity(cartItem.getQuantity() - 1);
            return cartRepository.save(cartItem);
        } else {
            cartRepository.delete(cartItem);
            return null; // Return null to indicate item was removed
        }
    }

}
