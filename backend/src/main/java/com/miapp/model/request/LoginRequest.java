package com.miapp.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class LoginRequest {
	
	@NotNull(message = "El usuario no debe ser nulo")
	@NotEmpty(message = "El usuario no debe ser vacio")
	private String username;
	
	@NotNull(message = "La clave no debe ser nulo")
	@NotEmpty(message = "L clave no debe ser vacio")
	private String password;
}
