package com.dam.tienda.services;

import com.dam.tienda.entities.Venta;
import com.dam.tienda.repositories.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class VentaServiceImpl implements VentaService {

    @Autowired
    private VentaRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Venta> findAll() {
        return (List<Venta>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Venta> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Venta> findByVideojuego(Integer videojuegoId) {
        return repository.findByVideojuegoId(videojuegoId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Venta> findByFecha(Date fecha) {
        return repository.findByFechaVenta(fecha);
    }

    @Override
    @Transactional
    public Venta save(Venta venta) {
        return repository.save(venta);
    }

    @Override
    @Transactional
    public Optional<Venta> delete(Integer id) {
        Optional<Venta> ventaOpt = repository.findById(id);
        ventaOpt.ifPresent(repository::delete);
        return ventaOpt;
    }
}
