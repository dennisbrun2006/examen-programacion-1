package com.uninorte.examenprogramacion1.service;

import com.uninorte.examenprogramacion1.model.Producto;
import com.uninorte.examenprogramacion1.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public Page<Producto> listarProductos(String nombreFiltro, Long categoriaId, Pageable pageable) {
        // Inicia con una especificación vacía (equivalente a "true")
        Specification<Producto> spec = (root, query, cb) -> cb.conjunction();

        // Filtro por nombre (LIKE)
        if (nombreFiltro != null && !nombreFiltro.isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("nombre")), "%" + nombreFiltro.toLowerCase() + "%"));
        }

        // Filtro por categoría (ID)
        if (categoriaId != null && categoriaId > 0) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("categoria").get("id"), categoriaId));
        }

        return productoRepository.findAll(spec, pageable);
    }

    public Optional<Producto> obtenerPorId(Long id) {
        return productoRepository.findById(id);
    }

    public Producto guardar(Producto producto) {
        return productoRepository.save(producto);
    }

    public void eliminar(Long id) {
        productoRepository.deleteById(id);
    }
}
