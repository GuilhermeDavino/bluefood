package com.blue.bluefood.api.controller;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.blue.bluefood.domain.exception.NegocioException;
import com.blue.bluefood.domain.exception.RestauranteNaoEncontradoException;
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
		return ResponseEntity.ok(restauranteService.BuscarOuFalhar(id));
	}
	
	@PostMapping
	public ResponseEntity<Restaurante> adicionar(@RequestBody Restaurante restaurante) {
		restaurante = restauranteService.adicionar(restaurante);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(restaurante.getId())
				.toUri();
		return ResponseEntity.created(uri).body(restaurante);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Restaurante> atualizar(@PathVariable("id") Long restauranteId, @RequestBody Restaurante restaurante) {
		Restaurante restauranteAtual = restauranteService.BuscarOuFalhar(restauranteId);
		BeanUtils.copyProperties(restauranteAtual, restaurante,
				"id", "formasPagamento", "produtos",
				"dataCadastro", "dataAtualizacao", "endereco");
		try {
			return ResponseEntity.ok(restauranteService.atualizar(restaurante));
		} catch (RestauranteNaoEncontradoException exception) {
			throw new NegocioException(exception.getMessage(), exception);
		}
	
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<?> atualizarParcial(@PathVariable Long id, @RequestBody Map<String, Object> campos) {
		@SuppressWarnings("unused")
		Restaurante restauranteEntity = restauranteService.BuscarOuFalhar(id);
		return ResponseEntity.ok(restauranteService.atualizarParcial(id, campos));	
	}

	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		restauranteService.deletar(id);
		return ResponseEntity.noContent().build();
	}
	
	
	@GetMapping("/listar-por-parametros")
	public ResponseEntity<List<Restaurante>> listarTest(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {
		return ResponseEntity.ok(restauranteRepository.find(nome, taxaInicial, taxaFinal));
	}
	
	@GetMapping("/com-frete-gratis")
	public ResponseEntity<List<Restaurante>> listarComFreteGratis(String nome) {
		return ResponseEntity.ok(restauranteRepository.listarComFreteGratis(nome));
	}
	
	
}
