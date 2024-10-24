package com.miapp.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.miapp.JwtUtil;
import com.miapp.model.request.LoginRequest;
import com.miapp.response.AuthResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;

	public AuthResponse authenticateAndGenerateToken(LoginRequest authRequest) {
		
		try {
			// Intentar autenticar con las credenciales proporcionadas
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

			String generateToken = jwtUtil.generateToken(authRequest.getUsername());
			AuthResponse token = new AuthResponse();
			token.setToken(generateToken);
			return token;
		} catch (AuthenticationException e) {
			// Si la autenticación falla, lanzar una excepción
			throw new BadCredentialsException("Usuario o contraseña incorrectos");
		}
	}
}
