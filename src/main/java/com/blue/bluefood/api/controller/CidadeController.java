package com.blue.bluefood.api.controller;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.blue.bluefood.api.exceptionhandler.Problem;
import com.blue.bluefood.domain.exception.EntidadeNaoEncontradaException;
import com.blue.bluefood.domain.exception.EstadoNaoEncontradoException;
import com.blue.bluefood.domain.exception.NegocioException;
import com.blue.bluefood.domain.model.Cidade;
import com.blue.bluefood.domain.repository.CidadeRepositoryQueries;
import com.blue.bluefood.domain.service.CidadeService;

@RestController
@RequestMapping("/cidades")
public class CidadeController {
	
	@Autowired
	private CidadeRepositoryQueries cidadeRepository;
	
	@Autowired
	private CidadeService cidadeService;
	
	
	@GetMapping
	public ResponseEntity<List<Cidade>> listar() {
		return ResponseEntity.ok(cidadeRepository.listar());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
		Cidade cidade = cidadeRepository.buscarPorId(id);
		return ResponseEntity.ok(cidade);
	}
	
	@PostMapping
	public ResponseEntity<Cidade> adicionar(@RequestBody Cidade cidade) {
		cidade = cidadeService.adicionar(cidade);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(cidade.getId())
				.toUri();
		return ResponseEntity.created(uri).body(cidade);
		
	}
	
	@PutMapping("/{id}")
	public Cidade atualizar(@PathVariable("id") Long cidadeId, @RequestBody Cidade cidade) {
		try {
			var cidadeatual = cidadeService.buscarOuFalhar(cidadeId);
			BeanUtils.copyProperties(cidade, cidadeatual, "id");
			return cidadeService.atualizar(cidade);
		} catch (EstadoNaoEncontradoException exception) {
			throw new NegocioException(exception.getMessage(), exception);
		}
		
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		cidadeService.remover(id);
	}
	
	
	
}
