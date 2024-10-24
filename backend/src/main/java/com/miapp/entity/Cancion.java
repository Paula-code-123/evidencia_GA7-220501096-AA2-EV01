package com.miapp.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Cancion {

	@Id
	private Long id;

	private String titulo;
	private String artista;
	private String album;
	private int anno;
	private String genero;

	@ManyToMany(mappedBy = "canciones")
    private List<ListaReproduccion> listasReproduccion = new ArrayList<>();
}
