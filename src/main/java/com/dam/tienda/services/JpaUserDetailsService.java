package com.dam.tienda.services;

import com.dam.tienda.entities.User;
import com.dam.tienda.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repository;
    @Transactional(readOnly = true)
    @Override
    // Se valida el usuario
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        Optional<User> userOptional = repository.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException(
                    String.format("Usuario %s no existe en el sistema", username));
        }
        User user = userOptional.orElseThrow();
        System.out.println("Details:" + user.getUsername());
        // Hay que convertir a una lista
     List<GrantedAuthority> roles = user.getRoles().stream()
             .map(role -> new SimpleGrantedAuthority(role.getName()))
             .collect(Collectors.toList());
     return new org.springframework.security.core.userdetails.User
             (user.getUsername(),
              user.getPassword(),
             user.isEnabled(),
             true, true,
                     true, roles);
    }
}
