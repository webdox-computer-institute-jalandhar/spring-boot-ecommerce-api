package com.example.demo.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message="username cannot be blank")
    @NonNull
    @Column(unique = true, nullable = false)
    private String username;

    @NotBlank(message = "email cannot be blank")
    @NonNull
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "password cannot be blank")
    @NonNull
    @Column(nullable = false)
    private String password;

//    @ManyToMany(fetch = FetchType.EAGER)
//     @JoinTable(name = "user_roles",
//                joinColumns = @JoinColumn(name = "user_id"),
//                inverseJoinColumns = @JoinColumn(name = "role_id"))
//     private Set<Role> roles = new HashSet<>();


}
