package com.miapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductoRequest {
	public String nombre;
	public String descripcion;
	public Double precio;
	public Integer stock;
	public Integer idCategoria;
}
