package com.dam.tienda.services;

import com.dam.tienda.entities.Videojuego;
import java.util.List;
import java.util.Optional;

public interface VideojuegoService {
    List<Videojuego> findAll();
    Optional<Videojuego> findById(Integer id);
    List<Videojuego> findByCategoria(Integer categoriaId);
    Videojuego save(Videojuego videojuego);
    Optional<Videojuego> update(Integer id, Videojuego videojuego);
    Optional<Videojuego> delete(Integer id);
}
