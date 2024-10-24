package com.miapp.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListaReproduccionDTO {

	private Long id;
	private String nombre;
	private String descripcion;
	private List<CancionDTO> canciones;
}
