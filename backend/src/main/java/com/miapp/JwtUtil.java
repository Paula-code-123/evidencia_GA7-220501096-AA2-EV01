package com.miapp;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtUtil {
	// Clave secreta en formato Base64
	 private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	
	// Generar un token JWT basado en el username
	public String generateToken(String username) {
		return Jwts.builder().setSubject(username).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Token válido por 10 horas
				.signWith(secretKey).compact();
	}

	// Validar el token JWT
	public boolean validateToken(String token, String username) {
		String tokenUsername = extractUsername(token);
		return (username.equals(tokenUsername) && !isTokenExpired(token));
	}

	// Extraer el nombre de usuario del token JWT
	public String extractUsername(String token) {
		return getParser().parseClaimsJws(token).getBody().getSubject();
	}

	// Verificar si el token ha expirado
	private boolean isTokenExpired(String token) {
		return getParser().parseClaimsJws(token).getBody().getExpiration().before(new Date());
	}

	// Configurar el parser del JWT con la clave de firma
	private JwtParser getParser() {
		return Jwts.parserBuilder().setSigningKey(secretKey).build();
	}
}
