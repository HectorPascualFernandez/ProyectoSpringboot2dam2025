package com.dam.tienda.controllers;


import com.dam.tienda.entities.User;
import com.dam.tienda.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
@Autowired
private UserService service;

@GetMapping
public List<User> list(){
   return service.findAll();
}

// Permite crear un usuario
// Esté metodo es privado
@PostMapping
public ResponseEntity<?> create(@Valid @RequestBody User user, BindingResult result){

    if (result.hasFieldErrors()) {
        return validation(result);

    }
    return ResponseEntity.status(HttpStatus.CREATED).body(service.save(user));
}
//Método es público
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user, BindingResult result){

        if (result.hasFieldErrors()) {
            return validation(result);

        }
        // Nos aseguramos para que no sea admin.
        // Para que nadie de alta un administrador del API Rest
        user.setAdmin(false);
        return create(user, result);
    }


    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " "+ err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }




}
