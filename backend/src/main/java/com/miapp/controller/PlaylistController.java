package com.miapp.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.miapp.model.dto.CancionDTO;
import com.miapp.model.dto.ListaReproduccionDTO;
import com.miapp.model.request.ListaReproduccionRequest;
import com.miapp.service.PlaylistService;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/api/v1/lists")
@AllArgsConstructor
public class PlaylistController {

	private final PlaylistService playlistService;

	@PostMapping()
	public ResponseEntity<ListaReproduccionDTO> crearLista(
			@Validated @RequestBody @NotNull ListaReproduccionRequest request) {
		ListaReproduccionDTO listaReproduccionDTO = playlistService.crearListaReproduccion(request);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(listaReproduccionDTO.getId()).toUri();

		return ResponseEntity.created(location).body(listaReproduccionDTO);
	}
	
	@GetMapping()
	public ResponseEntity<List<ListaReproduccionDTO>> verListasReproduccion() {
		return ResponseEntity.ok(playlistService.verListasReproduccion());
	}

	@GetMapping("/{idList}")
	public ResponseEntity<ListaReproduccionDTO> getListaReproduccionPorId(@PathVariable Long idList) {
		return ResponseEntity.ok(playlistService.verListaReproduccionPorId(idList));
	}
	
	@DeleteMapping("/{idList}")
	public ResponseEntity<Void> eliminarListaReproduccion(@PathVariable Long idList) {
		playlistService.eliminarListaReproduccion(idList);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("listar-canciones")
	public ResponseEntity<List<CancionDTO>> verCanciones() {
		return ResponseEntity.ok(playlistService.verCanciones());
	}
}
