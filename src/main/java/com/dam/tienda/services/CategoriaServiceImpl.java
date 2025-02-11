package com.dam.tienda.services;

import com.dam.tienda.entities.Categoria;
import com.dam.tienda.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Categoria> findAll() {
        return (List<Categoria>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Categoria> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Categoria save(Categoria categoria) {
        return repository.save(categoria);
    }

    @Override
    @Transactional
    public Optional<Categoria> update(Integer id, Categoria categoria) {
        Optional<Categoria> categoriaOpt = repository.findById(id);
        if (categoriaOpt.isPresent()) {
            Categoria categoriaDb = categoriaOpt.get();
            categoriaDb.setNombre(categoria.getNombre());
            categoriaDb.setDescripcion(categoria.getDescripcion());
            return Optional.of(repository.save(categoriaDb));
        }
        return categoriaOpt;
    }

    @Override
    @Transactional
    public Optional<Categoria> delete(Integer id) {
        Optional<Categoria> categoriaOpt = repository.findById(id);
        categoriaOpt.ifPresent(repository::delete);
        return categoriaOpt;
    }
}
