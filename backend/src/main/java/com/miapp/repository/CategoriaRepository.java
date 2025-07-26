package com.miapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.miapp.entity.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
	
}