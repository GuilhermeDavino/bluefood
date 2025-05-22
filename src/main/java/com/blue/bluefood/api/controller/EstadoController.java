package com.blue.bluefood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blue.bluefood.domain.exception.EntidadeNaoEncontradaException;
import com.blue.bluefood.domain.model.Estado;
import com.blue.bluefood.domain.repository.EstadoRepository;

@RestController
@RequestMapping("/estados")
public class EstadoController {
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@GetMapping
	public ResponseEntity<List<Estado>> listar() {
		return ResponseEntity.ok(estadoRepository.listar());
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Estado> buscarPorId(@PathVariable Long id) {
		try {
			Estado estado = estadoRepository.buscarPorId(id);
			return ResponseEntity.ok(estado);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}
		
	}
}
