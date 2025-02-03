package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne   // Many Product in One CartItem
    private Product product;

    private int quantity;


    @ManyToOne // Many CartItems refer to One Order
    @JoinColumn(name = "order_id") // Column to store the reference to the order
    @JsonBackReference // Prevents infinite recursion when serializing the order
    private Order order;

}
