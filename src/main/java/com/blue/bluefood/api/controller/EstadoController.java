package com.blue.bluefood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blue.bluefood.domain.model.Estado;
import com.blue.bluefood.domain.repository.EstadoRepository;

@RestController
@RequestMapping("/estados")
public class EstadoController {
	
	@Autowired
	private EstadoRepository repository;
	
	@GetMapping
	public List<Estado> listar() {
		return repository.listar();
	}
}
