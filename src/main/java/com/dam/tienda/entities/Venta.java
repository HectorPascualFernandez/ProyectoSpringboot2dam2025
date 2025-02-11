package com.dam.tienda.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Data
@Entity
@Table(name = "ventas")
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "videojuego_id", nullable = false)
    private Videojuego videojuego;

    @Column(name = "fecha_venta", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaVenta;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(name = "precio_total", nullable = false)
    private Double precioTotal;

    @Column(name = "nombre_cliente", nullable = false, length = 100)
    private String nombreCliente;
}