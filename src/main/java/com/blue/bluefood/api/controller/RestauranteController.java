package com.blue.bluefood.api.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.blue.bluefood.domain.exception.EntidadeNaoEncontradaException;
import com.blue.bluefood.domain.model.Restaurante;
import com.blue.bluefood.domain.repository.RestauranteRepository;
import com.blue.bluefood.domain.service.RestauranteService;
@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired 
	private RestauranteService restauranteService;
	
	@GetMapping
	public ResponseEntity<List<Restaurante>> listar() {
		return ResponseEntity.ok(restauranteRepository.todos());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Restaurante> buscarPorId(@PathVariable Long id) {
		return ResponseEntity.ok(restauranteRepository.buscarPorId(id));
	}
	
	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante) {
		
		try {
			restaurante = restauranteService.adicionar(restaurante);
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(restaurante.getId()).toUri();
			return ResponseEntity.created(uri).body(restaurante);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Restaurante restaurante) {
		try {
			return ResponseEntity.ok(restauranteService.atualizar(id, restaurante));
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		restauranteService.deletar(id);
		return ResponseEntity.noContent().build();
	}
}
