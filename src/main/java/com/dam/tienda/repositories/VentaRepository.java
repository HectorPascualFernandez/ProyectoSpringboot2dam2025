package com.dam.tienda.repositories;

import com.dam.tienda.entities.Venta;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Date;
import java.util.List;

public interface VentaRepository extends CrudRepository<Venta, Integer> {

    List<Venta> findByVideojuegoId(Integer videojuegoId);

    List<Venta> findByFechaVenta(Date fecha);

    List<Venta> findByFechaVentaBetween(Date fechaInicio, Date fechaFin);

    @Query("SELECT v FROM Venta v WHERE v.videojuego.id = ?1 AND v.fechaVenta BETWEEN ?2 AND ?3")
    List<Venta> findVentasByVideojuegoAndFechaBetween(Integer videojuegoId, Date fechaInicio, Date fechaFin);
}
