package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.CartItem;
import com.example.demo.model.Product;

public interface CartRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByProduct(Product product);


    // @Query("SELECT c FROM CartItem c WHERE c.product.id = :productId")
    // Optional<CartItem> findByProductId(@Param("productId") Long productId);

}
