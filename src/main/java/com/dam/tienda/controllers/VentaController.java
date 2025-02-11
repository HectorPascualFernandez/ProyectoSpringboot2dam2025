package com.dam.tienda.controllers;

import com.dam.tienda.entities.Venta;
import com.dam.tienda.services.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @GetMapping
    public ResponseEntity<List<Venta>> getAllVentas() {
        return ResponseEntity.ok(ventaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venta> getVentaById(@PathVariable Integer id) {
        return ventaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/videojuego/{videojuegoId}")
    public ResponseEntity<List<Venta>> getVentasByVideojuego(@PathVariable Integer videojuegoId) {
        return ResponseEntity.ok(ventaService.findByVideojuego(videojuegoId));
    }

    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<Venta>> getVentasByFecha(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha) {
        return ResponseEntity.ok(ventaService.findByFecha(fecha));
    }

    @PostMapping
    public ResponseEntity<Venta> createVenta(@RequestBody Venta venta) {
        return ResponseEntity.ok(ventaService.save(venta));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVenta(@PathVariable Integer id) {
        return ventaService.delete(id)
                .map(v -> ResponseEntity.ok().<Void>build())
                .orElse(ResponseEntity.notFound().build());
    }
}