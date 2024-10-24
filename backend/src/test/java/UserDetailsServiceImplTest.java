import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.miapp.entity.Usuario;
import com.miapp.repository.UsuarioRepository;
import com.miapp.service.UserDetailsServiceImpl;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

	@Mock
	private UsuarioRepository usuarioRepository;

	@InjectMocks
	private UserDetailsServiceImpl userDetailsService;

	private Usuario usuario;

	@BeforeEach
	void setUp() {
		usuario = new Usuario();
		usuario.setUsername("testuser");
		usuario.setPassword("testpassword");
	}

	@Test
	void testLoadUserByUsername_Success() {
		// Simular que el usuario existe en el repositorio
		when(usuarioRepository.findByUsername(anyString())).thenReturn(Optional.of(usuario));

		// Llamar al método que carga el usuario
		UserDetails userDetails = userDetailsService.loadUserByUsername(usuario.getUsername());

		// Verificar que el username y password son correctos
		assertEquals(usuario.getUsername(), userDetails.getUsername());
		assertEquals(usuario.getPassword(), userDetails.getPassword());

		// Verificar que findByUsername fue llamado una vez con el nombre de usuario
		// correcto
		verify(usuarioRepository, times(1)).findByUsername("testuser");
	}

	@Test
	void testLoadUserByUsername_UserNotFound() {
		// Simular que el usuario no se encuentra en el repositorio
		when(usuarioRepository.findByUsername(anyString())).thenReturn(Optional.empty());

		// Verificar que se lanza una excepción UsernameNotFoundException
		UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> {
			userDetailsService.loadUserByUsername(usuario.getUsername());
		});
		
		assertEquals("Usuario no encontrado", exception.getMessage());
		
		// Verificar que findByUsername fue llamado una vez
		verify(usuarioRepository, times(1)).findByUsername(usuario.getUsername());
	}
}
