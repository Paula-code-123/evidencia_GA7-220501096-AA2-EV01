package com.miapp.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.miapp.entity.Cancion;
import com.miapp.entity.ListaReproduccion;
import com.miapp.model.dto.CancionDTO;
import com.miapp.model.dto.ListaReproduccionDTO;
import com.miapp.model.request.ListaReproduccionRequest;
import com.miapp.repository.CancionRepository;
import com.miapp.repository.ListaReproduccionRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlaylistService {

	private final ListaReproduccionRepository listaReproduccionRepository;
	private final CancionRepository cancionRepository;

	@Transactional
	public ListaReproduccionDTO crearListaReproduccion(ListaReproduccionRequest listaDTO) {
		try {
			if (listaDTO.getCancionesIds() == null || listaDTO.getCancionesIds().isEmpty()) {
				throw new IllegalArgumentException("La lista de reproducción debe tener al menos una canción");
			}

			List<Cancion> canciones = cancionRepository.findAllById(listaDTO.getCancionesIds());

			// Verificar si hay IDs no encontrados
			if (canciones.size() != listaDTO.getCancionesIds().size()) {
				throw new IllegalArgumentException("Algunos IDs de canciones no existen en la base de datos.");
			}

			ListaReproduccion lista = new ListaReproduccion();
			lista.setNombre(listaDTO.getNombre());
			lista.setDescripcion(listaDTO.getDescripcion());
			lista.getCanciones().addAll(canciones);

			lista = listaReproduccionRepository.save(lista);

			return mapearListaReproduccion(lista);
		} catch (IllegalArgumentException e) {
			// Captura de excepciones personalizadas, como parámetros inválidos
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (Exception e) {
			// Captura de cualquier otra excepción general
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Error al crear la lista de reproducción", e);
		}
	}

	public List<ListaReproduccionDTO> verListasReproduccion() {
		List<ListaReproduccion> listas = listaReproduccionRepository.findAll();
		return listas.stream().map(this::mapearListaReproduccion).toList();
	}

	public ListaReproduccionDTO verListaReproduccionPorId(Long id) {
		ListaReproduccion lista = listaReproduccionRepository.findById(id).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lista de reproducción no encontrada"));

		return mapearListaReproduccion(lista);
	}

	// Método para mapear una lista de reproducción
	private ListaReproduccionDTO mapearListaReproduccion(ListaReproduccion lista) {
		ListaReproduccionDTO dto = new ListaReproduccionDTO();
		dto.setId(lista.getId());
		dto.setNombre(lista.getNombre());
		dto.setDescripcion(lista.getDescripcion());
		dto.setCanciones(mapearCanciones(lista.getCanciones()));
		return dto;
	}

	// Método genérico para mapear una lista de canciones
	private List<CancionDTO> mapearCanciones(List<Cancion> canciones) {
		return canciones.stream().map(this::mapearCancion).toList();
	}

	// Método para mapear una canción
	private CancionDTO mapearCancion(Cancion cancion) {
		CancionDTO cancionDTO = new CancionDTO();
		cancionDTO.setId(cancion.getId());
		cancionDTO.setTitulo(cancion.getTitulo());
		cancionDTO.setArtista(cancion.getArtista());
		cancionDTO.setAnno(cancion.getAnno());
		cancionDTO.setGenero(cancion.getGenero());
		return cancionDTO;
	}

	@Transactional
	public void eliminarListaReproduccion(Long id) {
		try {
			ListaReproduccion lista = listaReproduccionRepository.findById(id)
					.orElseThrow(() -> new IllegalArgumentException("Lista de reproducción no encontrada"));
			listaReproduccionRepository.delete(lista);
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Error al eliminar la lista de reproducción", e);
		}
	}

	public List<CancionDTO> verCanciones() {
		List<Cancion> canciones = cancionRepository.findAll();
		return mapearCanciones(canciones);
	}
}
