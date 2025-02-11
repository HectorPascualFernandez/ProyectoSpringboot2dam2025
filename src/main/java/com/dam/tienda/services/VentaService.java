package com.dam.tienda.services;

import com.dam.tienda.entities.Venta;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface VentaService {
    List<Venta> findAll();
    Optional<Venta> findById(Integer id);
    List<Venta> findByVideojuego(Integer videojuegoId);
    List<Venta> findByFecha(Date fecha);
    Venta save(Venta venta);
    Optional<Venta> delete(Integer id);
}
