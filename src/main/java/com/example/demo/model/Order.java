package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "order") // mappedBy links to CartItem's 'order' field
    @JsonManagedReference  // Prevents recursion by serializing CartItems properly
    private List<CartItem> cartItems;

    private double totalPrice;
}
