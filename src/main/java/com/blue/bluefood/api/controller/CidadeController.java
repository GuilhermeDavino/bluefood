package com.blue.bluefood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blue.bluefood.domain.model.Cidade;
import com.blue.bluefood.domain.repository.CidadeRepository;

@RestController
@RequestMapping("/cidades")
public class CidadeController {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@GetMapping
	public ResponseEntity<List<Cidade>> listar() {
		return ResponseEntity.ok(cidadeRepository.listar());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
		Cidade cidade = cidadeRepository.buscarPorId(id);
		if(cidade == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("A cidade de id %d não foi encontrada ou não existe!", id));
		return ResponseEntity.ok(cidade);
	}
	
	
	
}
