import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.miapp.entity.Cancion;
import com.miapp.entity.ListaReproduccion;
import com.miapp.model.dto.CancionDTO;
import com.miapp.model.dto.ListaReproduccionDTO;
import com.miapp.model.request.ListaReproduccionRequest;
import com.miapp.repository.CancionRepository;
import com.miapp.repository.ListaReproduccionRepository;
import com.miapp.service.PlaylistService;

@ExtendWith(MockitoExtension.class)
class PlaylistServiceTest {

	@Mock
	private ListaReproduccionRepository listaReproduccionRepository;

	@Mock
	private CancionRepository cancionRepository;

	@InjectMocks
	private PlaylistService playlistService;

	private ListaReproduccionRequest request;
	private Cancion cancion1;
	private Cancion cancion2;

	@BeforeEach
	void setUp() {
		request = new ListaReproduccionRequest();
		request.setNombre("Lista de prueba");
		request.setDescripcion("Descripción de prueba");
		request.setCancionesIds(List.of(1L, 2L));

		cancion1 = new Cancion();
		cancion1.setId(1L);
		cancion2 = new Cancion();
		cancion2.setId(2L);
	}

	@Test
	void testCrearListaReproduccion() {
		when(cancionRepository.findAllById(request.getCancionesIds())).thenReturn(List.of(cancion1, cancion2));

		when(listaReproduccionRepository.save(any(ListaReproduccion.class))).thenAnswer(invocation -> {
			ListaReproduccion lista = invocation.getArgument(0);
			lista.setId(1L); // Simular que se guarda y asigna ID
			return lista;
		});

		ListaReproduccionDTO result = playlistService.crearListaReproduccion(request);
		System.out.println(result);
		assertNotNull(result);
		assertEquals("Lista de prueba", result.getNombre());
		assertEquals(2, result.getCanciones().size());
		verify(listaReproduccionRepository, times(1)).save(any(ListaReproduccion.class));
	}

	@Test
	void testCrearListaReproduccion_ShouldThrowError_WhenPlaylistHasNoSongs() {
		request.setCancionesIds(null);

		ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
			playlistService.crearListaReproduccion(request);
		});

		assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
		assertEquals("La lista de reproducción debe tener al menos una canción", exception.getReason());
	}

	@Test
	void testCrearListaReproduccion_ShouldThrowError_WhenPlaylistHasEmptySongs() {
		request.setCancionesIds(List.of());

		ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
			playlistService.crearListaReproduccion(request);
		});

		assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
		assertEquals("La lista de reproducción debe tener al menos una canción", exception.getReason());
	}

	@Test
	void testCrearListaReproduccion_Exception() {
		when(cancionRepository.findAllById(request.getCancionesIds()))
				.thenThrow(new RuntimeException("Error al buscar canciones"));

		ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
			playlistService.crearListaReproduccion(request);
		});

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
		assertEquals("Error al crear la lista de reproducción", exception.getReason());
	}

	@Test
	void testCrearListaReproduccion_ShouldThrowError_WhenSomeSongsNotFound() {
		when(cancionRepository.findAllById(request.getCancionesIds())).thenReturn(List.of(cancion1));

		ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
			playlistService.crearListaReproduccion(request);
		});

		assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
		assertEquals("Algunos IDs de canciones no existen en la base de datos.", exception.getReason());
	}

	@Test
	void eliminarListaReproduccion_ShouldThrowError_WhenPlaylistNotFound() {
		when(listaReproduccionRepository.findById(1L)).thenReturn(Optional.empty());

		ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
			playlistService.eliminarListaReproduccion(1L);
		});

		assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
		assertEquals("Lista de reproducción no encontrada", exception.getReason());
	}

	@Test
	void eliminarListaReproduccion_ShouldDeletePlaylist_WhenPlaylistExists() {
		ListaReproduccion lista = new ListaReproduccion();
		lista.setId(1L);

		when(listaReproduccionRepository.findById(1L)).thenReturn(Optional.of(lista));

		playlistService.eliminarListaReproduccion(1L);

		verify(listaReproduccionRepository, times(1)).delete(lista);
	}

	@Test
	void eliminarListaReproduccion_Exception() {
		when(listaReproduccionRepository.findById(1L)).thenThrow(new RuntimeException("Error al buscar lista"));

		ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
			playlistService.eliminarListaReproduccion(1L);
		});

		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatusCode());
		assertEquals("Error al eliminar la lista de reproducción", exception.getReason());
	}

	@Test
	void verListasReproduccion() {
		List<ListaReproduccion> listas = List.of(new ListaReproduccion(), new ListaReproduccion());
		when(listaReproduccionRepository.findAll()).thenReturn(listas);

		List<ListaReproduccionDTO> result = playlistService.verListasReproduccion();

		assertEquals(2, result.size());
		verify(listaReproduccionRepository, times(1)).findAll();
	}

	@Test
	void verListaReproduccionPorId() {
		ListaReproduccion lista = new ListaReproduccion();
		when(listaReproduccionRepository.findById(1L)).thenReturn(Optional.of(lista));

		ListaReproduccionDTO result = playlistService.verListaReproduccionPorId(1L);

		assertNotNull(result);
		verify(listaReproduccionRepository, times(1)).findById(1L);
	}

	@Test
	void verListaReproduccionPorId_ShouldThrowError_WhenPlaylistNotFound() {
		when(listaReproduccionRepository.findById(1L)).thenReturn(Optional.empty());

		ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
			playlistService.verListaReproduccionPorId(1L);
		});

		assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
		assertEquals("Lista de reproducción no encontrada", exception.getReason());
	}

	@Test
	void verCanciones() {
		List<Cancion> canciones = List.of(new Cancion(), new Cancion());
		when(cancionRepository.findAll()).thenReturn(canciones);

		List<CancionDTO> result = playlistService.verCanciones();

		assertEquals(2, result.size());
		verify(cancionRepository, times(1)).findAll();
	}
}
