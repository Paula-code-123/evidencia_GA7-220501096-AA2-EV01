package com.miapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miapp.model.request.LoginRequest;
import com.miapp.response.AuthResponse;
import com.miapp.service.AuthService;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/api/v1/auth")
@AllArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@Validated @RequestBody @NotNull LoginRequest loginRequest) {
		return ResponseEntity.ok(authService.authenticateAndGenerateToken(loginRequest));
	}
}
