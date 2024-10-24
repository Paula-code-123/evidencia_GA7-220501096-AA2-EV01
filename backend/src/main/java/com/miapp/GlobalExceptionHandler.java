package com.miapp;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		StringBuilder errors = new StringBuilder("Errores de validación: ");

		// Itera sobre los errores de validación
		ex.getBindingResult().getAllErrors().forEach(error -> {
			errors.append(error.getDefaultMessage()).append("; ");
		});

		return ResponseEntity.badRequest().body(errors.toString());
	}
	
	 @ExceptionHandler(BadCredentialsException.class)
	    public ResponseEntity<String> handleBadCredentials(BadCredentialsException ex) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
	    }
}
