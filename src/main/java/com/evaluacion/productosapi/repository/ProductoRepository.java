package com.evaluacion.productosapi.repository;

import com.evaluacion.productosapi.entity.Categoria;
import com.evaluacion.productosapi.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByCategoria(Categoria categoria);

    List<Producto> findByNombre(String nombre);
}
