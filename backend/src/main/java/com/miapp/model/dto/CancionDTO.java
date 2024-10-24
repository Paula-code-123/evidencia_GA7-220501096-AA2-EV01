package com.miapp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CancionDTO {

	private Long id;
	private String titulo;
	private String artista;
	private int anno;
	private String genero;
}
