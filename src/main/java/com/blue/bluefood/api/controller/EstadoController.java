package com.blue.bluefood.api.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.blue.bluefood.domain.model.Estado;
import com.blue.bluefood.domain.repository.EstadoRepositoryQueries;
import com.blue.bluefood.domain.service.EstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {
	
	@Autowired
	private EstadoRepositoryQueries estadoRepository;
	
	@Autowired
	private EstadoService estadoService;
	
	@GetMapping
	public ResponseEntity<List<Estado>> listar() {
		return ResponseEntity.ok(estadoRepository.listar());
	}
	
	@GetMapping(value = "/{id}")
	public Estado buscarPorId(@PathVariable("id") Long estadoId) {
			return estadoService.buscarOuFalhar(estadoId);
	}
	
	@PostMapping
	public ResponseEntity<Estado> adicionar(@RequestBody Estado estado) {
		estado = estadoService.adicionar(estado);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(estado.getId())
				.toUri();
		return ResponseEntity.created(uri).body(estado);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Estado> atualizar(@PathVariable Long id, @RequestBody Estado estado) {
		estado = estadoService.atualizar(id, estado);
		return ResponseEntity.ok(estado);
		
	}
	
	@DeleteMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@PathVariable Long id) {
		estadoService.deletar(id);	
	}
	
	
}
