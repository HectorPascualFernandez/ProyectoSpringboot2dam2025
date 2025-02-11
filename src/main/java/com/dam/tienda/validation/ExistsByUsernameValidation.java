package com.dam.tienda.validation;

import com.dam.tienda.services.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExistsByUsernameValidation implements
        ConstraintValidator<ExistsByUsername, String> {
    @Autowired
    private UserService service;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        System.out.println("Validating username: " + username);
        if (service == null) {
            return true; // Si el valor es null, no validamos
        }
        return !service.existsByUsername(username);
    }

}
