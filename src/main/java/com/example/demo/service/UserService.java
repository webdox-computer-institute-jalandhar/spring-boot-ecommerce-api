package com.example.demo.service;

import com.example.demo.model.User;

public interface UserService {
    User getUser(Long id);

    User saveUser(User user);
}
