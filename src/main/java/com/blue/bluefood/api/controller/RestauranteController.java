package com.blue.bluefood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blue.bluefood.domain.model.Restaurante;
import com.blue.bluefood.domain.repository.RestauranteRepository;
@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {
	
	@Autowired
	private RestauranteRepository repository;
	
	@GetMapping
	public List<Restaurante> listar() {
		return repository.todos();
	}
	
	@GetMapping("/{id}")
	public Restaurante buscarPorId(@PathVariable Long id) {
		return repository.porId(id);
	}
}
