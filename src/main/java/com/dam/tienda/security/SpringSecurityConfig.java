package com.dam.tienda.security;

import com.dam.tienda.security.filter.JwtAuthenticationFilter;
import com.dam.tienda.security.filter.JwtValidationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration

public class SpringSecurityConfig {
    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Es una notación de Srping que nos sirve para declarar un bean y registrarlo
    // en el contenedor de Spring. Se usa normalmente para instanciar alguna configuración
    // por defecto y están relacionadas con application.propierties
    @Bean
    PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }
  @Bean
  SecurityFilterChain filterChain( HttpSecurity http ) throws Exception {
        return http.authorizeHttpRequests((authz) -> authz
                // Permite método GET /api/users
                // Permite método POST /api/users/register
                .requestMatchers("/api/users").permitAll()
                .requestMatchers("/api/users/register").permitAll()
                // No es necesario poner el prefijo ROLE porque ya lo añade el método hasRole

                .requestMatchers("/api/coches").hasRole("USER")

                .anyRequest().authenticated())
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtValidationFilter(authenticationManager()))
                .csrf(config -> config.disable()) // Tenemos que deshabilitar porque es api-rest
                // Como manejar las sesiones en Spring Security
                //No crean sesiones para los usuarios
                // Aquí vamos a crear un token sin estado
                .sessionManagement(management ->
                        management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();

  }

}
