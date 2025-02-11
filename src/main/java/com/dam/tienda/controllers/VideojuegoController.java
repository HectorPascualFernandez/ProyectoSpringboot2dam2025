package com.dam.tienda.controllers;

import com.dam.tienda.entities.Videojuego;
import com.dam.tienda.services.VideojuegoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/videojuegos")
public class VideojuegoController {

    @Autowired
    private VideojuegoService videojuegoService;

    @GetMapping
    public ResponseEntity<List<Videojuego>> getAllVideojuegos() {
        return ResponseEntity.ok(videojuegoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Videojuego> getVideojuegoById(@PathVariable Integer id) {
        return videojuegoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<Videojuego>> getVideojuegosByCategoria(@PathVariable Integer categoriaId) {
        return ResponseEntity.ok(videojuegoService.findByCategoria(categoriaId));
    }

    @PostMapping
    public ResponseEntity<Videojuego> createVideojuego(@RequestBody Videojuego videojuego) {
        return ResponseEntity.ok(videojuegoService.save(videojuego));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Videojuego> updateVideojuego(@PathVariable Integer id, @RequestBody Videojuego videojuego) {
        return videojuegoService.update(id, videojuego)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVideojuego(@PathVariable Integer id) {
        return videojuegoService.delete(id)
                .map(v -> ResponseEntity.ok().<Void>build())
                .orElse(ResponseEntity.notFound().build());
    }
}
