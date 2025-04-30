package com.blue.bluefood.api.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
	public ResponseEntity<Cozinha> buscar(@PathVariable("id") Long id) {
		Cozinha cozinha = repository.porId(id);
		if(cozinha != null) {
			return ResponseEntity.ok(cozinha);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Cozinha> adicionar(@RequestBody Cozinha cozinha) {
		cozinha = repository.adicionar(cozinha);
		 URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
		            .path("/{id}")
		            .buildAndExpand(cozinha.getId())
		            .toUri();
		return ResponseEntity.created(uri).body(cozinha);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Cozinha> atualizar(@PathVariable Long id, @RequestBody Cozinha cozinha) {
		Cozinha cozinhaAtual = repository.porId(id);
		if(cozinhaAtual != null) {
			BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
			
			cozinhaAtual = repository.adicionar(cozinhaAtual);
			return ResponseEntity.ok(cozinhaAtual);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		try {
			Cozinha cozinha = repository.porId(id);
			if(cozinha != null) {
				repository.remover(cozinha);
				return ResponseEntity.noContent().build();
			}
			
			return ResponseEntity.notFound().build();
		} catch (DataIntegrityViolationException exception) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		
	}
}

