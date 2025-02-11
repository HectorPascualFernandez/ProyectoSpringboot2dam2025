package com.dam.tienda.services;

import com.dam.tienda.entities.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User save(User user);
    boolean existsByUsername(String username);

}
