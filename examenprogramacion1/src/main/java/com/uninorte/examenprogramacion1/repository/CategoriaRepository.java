package com.uninorte.examenprogramacion1.repository;

import com.uninorte.examenprogramacion1.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
