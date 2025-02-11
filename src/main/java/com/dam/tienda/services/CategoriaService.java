package com.dam.tienda.services;

import com.dam.tienda.entities.Categoria;
import java.util.List;
import java.util.Optional;

public interface CategoriaService {
    List<Categoria> findAll();
    Optional<Categoria> findById(Integer id);
    Categoria save(Categoria categoria);
    Optional<Categoria> update(Integer id, Categoria categoria);
    Optional<Categoria> delete(Integer id);
}
