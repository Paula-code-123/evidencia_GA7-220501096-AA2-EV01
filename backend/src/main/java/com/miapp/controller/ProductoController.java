package com.miapp.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miapp.dto.ProductoRequest;
import com.miapp.entity.Producto;
import com.miapp.service.ProductoService;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoController {

	private final ProductoService productoService;

	public ProductoController(ProductoService productoService) {
		this.productoService = productoService;
	}

	@PostMapping
	public ResponseEntity<Map<String, String>> crearProducto(@RequestBody ProductoRequest request) {
		productoService.crearProducto(request);

		Map<String, String> respuesta = new HashMap<>();
		respuesta.put("mensaje", "Producto creado exitosamente");

		return ResponseEntity.ok(respuesta);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Producto> obtenerProducto(@PathVariable Integer id) {
	    Producto producto = productoService.obtenerProductoPorId(id);
	    return ResponseEntity.ok(producto);
	}


	@PutMapping("/{id}")
	public ResponseEntity<Map<String, String>> actualizarProducto(@PathVariable Integer id,
			@RequestBody ProductoRequest request) {
		productoService.actualizarProducto(id, request);

		Map<String, String> respuesta = new HashMap<>();
		respuesta.put("mensaje", "Producto actualizado exitosamente");

		return ResponseEntity.ok(respuesta);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, String>> eliminarProducto(@PathVariable Integer id) {
		productoService.eliminarProducto(id);

		Map<String, String> respuesta = new HashMap<>();
		respuesta.put("mensaje", "Producto eliminado exitosamente");

		return ResponseEntity.ok(respuesta);
	}

}
