package com.blue.bluefood.api.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.blue.bluefood.api.model.CozinhasXmlWrapper;
import com.blue.bluefood.domain.exception.EntidadeEmUsoException;
import com.blue.bluefood.domain.exception.EntidadeNaoEncontradaException;
import com.blue.bluefood.domain.model.Cozinha;
import com.blue.bluefood.domain.repository.CozinhaRepository;
import com.blue.bluefood.domain.service.CozinhaService;

@RestController
@RequestMapping(value = "/cozinhas"/*, produces = MediaType.APPLICATION_JSON_VALUE*/)
public class CozinhaController {
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private CozinhaService cozinhaService;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Cozinha> listar() {
		return cozinhaRepository.todas();
	}
	
	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	public CozinhasXmlWrapper listarXml() {
		return new CozinhasXmlWrapper(cozinhaRepository.todas());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Cozinha> buscar(@PathVariable("id") Long id) {
		Cozinha cozinha = cozinhaRepository.buscarPorId(id);
		if(cozinha != null) {
			return ResponseEntity.ok(cozinha);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Cozinha> adicionar(@RequestBody Cozinha cozinha) {
		cozinha = cozinhaRepository.salvar(cozinha);
		 URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
		            .path("/{id}")
		            .buildAndExpand(cozinha.getId())
		            .toUri();
		return ResponseEntity.created(uri).body(cozinha);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Cozinha> atualizar(@PathVariable Long id, @RequestBody Cozinha cozinha) {
		
		
		Cozinha cozinhaAtual = cozinhaRepository.buscarPorId(id);
		try {
			BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
			cozinhaAtual = cozinhaService.salvar(cozinhaAtual);
			return ResponseEntity.ok(cozinhaAtual);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.notFound().build();
		}
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		try {
			cozinhaService.remover(id);
			return ResponseEntity.noContent().build();
			
		} catch (EntidadeEmUsoException exception) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		} catch (EntidadeNaoEncontradaException exception) {
			return ResponseEntity.notFound().build();
		}
		
	}
}

