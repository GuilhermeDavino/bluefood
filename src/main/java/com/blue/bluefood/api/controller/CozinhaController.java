package com.blue.bluefood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blue.bluefood.api.model.CozinhasXmlWrapper;
import com.blue.bluefood.domain.model.Cozinha;
import com.blue.bluefood.domain.repository.CozinhaRepository;

@RestController
@RequestMapping(value = "/cozinhas"/*, produces = MediaType.APPLICATION_JSON_VALUE*/)
public class CozinhaController {
	
	@Autowired
	private CozinhaRepository repository;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Cozinha> listar() {
		return repository.todas();
	}
	
	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	public CozinhasXmlWrapper listarXml() {
		return new CozinhasXmlWrapper(repository.todas());
	}
	
	@GetMapping("/{id}")
	public Cozinha buscar(@PathVariable("id") Long id) {
		return repository.porId(id);
	}
}
