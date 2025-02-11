package com.dam.tienda.repositories;

import com.dam.tienda.entities.Videojuego;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface VideojuegoRepository extends CrudRepository<Videojuego, Integer> {

    List<Videojuego> findByCategoriaId(Integer categoriaId);

    List<Videojuego> findByPrecioBetween(Double precioMin, Double precioMax);

    List<Videojuego> findByStockLessThan(Integer stockMinimo);
}
