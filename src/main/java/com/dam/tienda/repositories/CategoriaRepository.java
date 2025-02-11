package com.dam.tienda.repositories;

import com.dam.tienda.entities.Categoria;
import org.springframework.data.repository.CrudRepository;

public interface CategoriaRepository extends CrudRepository<Categoria, Integer> {
    // Los métodos básicos son proporcionados por CrudRepository
}
