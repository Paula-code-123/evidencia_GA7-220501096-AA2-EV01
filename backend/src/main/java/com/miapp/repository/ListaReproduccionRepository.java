package com.miapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.miapp.entity.ListaReproduccion;

@Repository
public interface ListaReproduccionRepository extends JpaRepository<ListaReproduccion, Long> {

}
