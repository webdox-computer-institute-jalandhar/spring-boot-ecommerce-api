package com.example.demo.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchasedItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Product product;

    private int quantity;

    @ManyToOne
    private Order order;

    private double price; // Price at the time of purchase

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

}
