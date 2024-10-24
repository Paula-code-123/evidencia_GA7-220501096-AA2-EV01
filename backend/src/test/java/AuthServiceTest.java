import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.miapp.JwtUtil;
import com.miapp.model.request.LoginRequest;
import com.miapp.response.AuthResponse;
import com.miapp.service.AuthService;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

	@Mock
	private AuthenticationManager authenticationManager;

	@Mock
	private JwtUtil jwtUtil;

	@InjectMocks
	private AuthService authService;

	private LoginRequest loginRequest;

	@BeforeEach
	void setUp() {
		loginRequest = new LoginRequest();
		loginRequest.setUsername("testuser");
		loginRequest.setPassword("testpassword");
	}

	@Test
	void testAuthenticateAndGenerateToken_Success() {
		// Simular que la autenticación fue exitosa
		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);

		// Simular la generación del token JWT
		when(jwtUtil.generateToken(loginRequest.getUsername())).thenReturn("mocked-jwt-token");

		// Llamar al método del servicio que genera el token
		AuthResponse response = authService.authenticateAndGenerateToken(loginRequest);

		// Verificar que el token generado sea el esperado
		assertEquals("mocked-jwt-token", response.getToken());

		// Verificar que el método 'authenticate' fue llamado una vez
		verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));

		// Verificar que 'generateToken' fue llamado una vez con el nombre de usuario
		// correcto
		verify(jwtUtil, times(1)).generateToken("testuser");
	}

	@Test
	void testAuthenticateAndGenerateToken_Failure() {
		// Simular que la autenticación falla
		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
				.thenThrow(new BadCredentialsException("Usuario o contraseña incorrectos"));

		// Verificar que se lanza la excepción BadCredentialsException
		assertThrows(BadCredentialsException.class, () -> {
			authService.authenticateAndGenerateToken(loginRequest);
		});

		// Verificar que 'authenticate' fue llamado una vez
		verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));

		// Verificar que 'generateToken' nunca fue llamado, ya que la autenticación
		// falló
		verify(jwtUtil, never()).generateToken(anyString());
	}
}
