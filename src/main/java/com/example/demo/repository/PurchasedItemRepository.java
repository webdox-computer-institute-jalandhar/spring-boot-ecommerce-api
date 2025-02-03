package com.example.demo.repository;

import com.example.demo.model.PurchasedItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchasedItemRepository extends JpaRepository<PurchasedItem, Long> {
}
