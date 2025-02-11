package com.dam.tienda.services;

import com.dam.tienda.entities.Videojuego;
import com.dam.tienda.repositories.VideojuegoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class VideojuegoServiceImpl implements VideojuegoService {

    @Autowired
    private VideojuegoRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Videojuego> findAll() {
        return (List<Videojuego>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Videojuego> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Videojuego> findByCategoria(Integer categoriaId) {
        return repository.findByCategoriaId(categoriaId);
    }

    @Override
    @Transactional
    public Videojuego save(Videojuego videojuego) {
        return repository.save(videojuego);
    }

    @Override
    @Transactional
    public Optional<Videojuego> update(Integer id, Videojuego videojuego) {
        Optional<Videojuego> videojuegoOpt = repository.findById(id);
        if (videojuegoOpt.isPresent()) {
            Videojuego videojuegoDb = videojuegoOpt.get();
            videojuegoDb.setTitulo(videojuego.getTitulo());
            videojuegoDb.setPrecio(videojuego.getPrecio());
            videojuegoDb.setStock(videojuego.getStock());
            videojuegoDb.setCategoria(videojuego.getCategoria());
            return Optional.of(repository.save(videojuegoDb));
        }
        return videojuegoOpt;
    }

    @Override
    @Transactional
    public Optional<Videojuego> delete(Integer id) {
        Optional<Videojuego> videojuegoOpt = repository.findById(id);
        videojuegoOpt.ifPresent(repository::delete);
        return videojuegoOpt;
    }
}
