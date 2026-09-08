package com.blue.bluefood.api.controller;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.groups.Default;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.validation.annotation.Validated;
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

import com.blue.bluefood.core.validation.Groups;
import com.blue.bluefood.core.validation.ValidacaoException;
import com.blue.bluefood.domain.exception.NegocioException;
import com.blue.bluefood.domain.exception.RestauranteNaoEncontradoException;
import com.blue.bluefood.domain.model.Restaurante;
import com.blue.bluefood.domain.repository.RestauranteRepository;
import com.blue.bluefood.domain.service.RestauranteService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired 
	private RestauranteService restauranteService;
	
	@Autowired
	private SmartValidator smartValidator;
	
	@GetMapping
	public ResponseEntity<List<Restaurante>> listar() {
		return ResponseEntity.ok(restauranteRepository.todos());
	}
	
	@GetMapping("/{restauranteId}")
	public ResponseEntity<Restaurante> buscarPorId(@PathVariable(name = "restauranteId") Long id) {
		return ResponseEntity.ok(restauranteService.BuscarOuFalhar(id));
	}
	
	@PostMapping
	public ResponseEntity<Restaurante> adicionar(@RequestBody @Valid Restaurante restaurante) {
		restaurante = restauranteService.adicionar(restaurante);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(restaurante.getId())
				.toUri();
		return ResponseEntity.created(uri).body(restaurante);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Restaurante> atualizar(@PathVariable("id") Long restauranteId, @RequestBody @Validated({ Default.class , Groups.RestauranteId.class }) Restaurante restaurante) {
		Restaurante restauranteAtual = restauranteService.BuscarOuFalhar(restauranteId);
		BeanUtils.copyProperties(restaurante, restauranteAtual,
				"id", "formasPagamento", "produtos",
				"dataCadastro", "dataAtualizacao", "endereco");
		
		try {
			return ResponseEntity.ok(restauranteService.atualizar(restauranteAtual));
		} catch (RestauranteNaoEncontradoException exception) {
			throw new NegocioException(exception.getMessage(), exception);
		}
	
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<?> atualizarParcial(@PathVariable Long id, @RequestBody Map<String, Object> campos, HttpServletRequest request) {
		
		var restauranteEntidade = restauranteService.BuscarOuFalhar(id);
		
		merge(campos, restauranteEntidade, request);
		validate(restauranteEntidade, "restaurante");
		restauranteEntidade = restauranteService.atualizar(restauranteEntidade);

		return ResponseEntity.ok(restauranteEntidade);	
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
	
	private void validate(Restaurante restaurante, String objectName) {
		var bindingResults = new BeanPropertyBindingResult(restaurante, 
				objectName);
		smartValidator.validate(restaurante, bindingResults);
		
		if(bindingResults.hasErrors()) {
			throw new ValidacaoException(bindingResults);
		}
	}
	
	
	private void merge(Map<String, Object> campos, Restaurante restauranteDestino, HttpServletRequest request) {
		var serverHttpRequest = new ServletServerHttpRequest(request);
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			
			objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
			
			Restaurante restauranteNovo = objectMapper.convertValue(campos, Restaurante.class);
			
			campos.forEach((chave, valor) -> {
				Field field = ReflectionUtils.findField(Restaurante.class, chave);
				
				field.setAccessible(true);
				
				Object novoValor = ReflectionUtils.getField(field, restauranteNovo);
				ReflectionUtils.setField(field, restauranteDestino, novoValor);
			});
		} catch (IllegalArgumentException exception) {
			
			Throwable rootCause = ExceptionUtils.getRootCause(exception);
			
			throw new HttpMessageNotReadableException(exception.getMessage(), rootCause, 
					serverHttpRequest);
		}
	}
	
	
}
