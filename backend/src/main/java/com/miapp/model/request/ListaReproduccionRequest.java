package com.miapp.model.request;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListaReproduccionRequest {

	@NotNull(message = "El nombre de la lista de reproducción no debe ser nulo")
	@NotEmpty(message = "El nombre de la lista de reproducción no debe ser vacio")
	private String nombre;

	@NotNull(message = "La descripción de la lista de reproducción no debe ser nulo")
	@NotEmpty(message = "La descripción de la lista de reproducción no debe ser vacio")
	private String descripcion;

	private List<Long> cancionesIds;
}
