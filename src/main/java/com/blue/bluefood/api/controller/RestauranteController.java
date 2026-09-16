package com.blue.bluefood.api.controller;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
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

import com.blue.bluefood.api.assembler.RestauranteDTOAssembler;
import com.blue.bluefood.api.assembler.RestauranteInputDisassembler;
import com.blue.bluefood.api.model.RestauranteDTO;
import com.blue.bluefood.api.model.RestauranteInputDTO;
import com.blue.bluefood.core.validation.ValidacaoException;
import com.blue.bluefood.domain.exception.CidadeNaoEncontradaException;
import com.blue.bluefood.domain.exception.CozinhaNaoEncontradaException;
import com.blue.bluefood.domain.exception.NegocioException;
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
	
	@Autowired
	private RestauranteDTOAssembler assembler;
	
	@Autowired
	private RestauranteInputDisassembler disassembler;
	
	@GetMapping
	public ResponseEntity<List<RestauranteDTO>> listar() {
		List<Restaurante> restaurantes = restauranteRepository.todos();
		List<RestauranteDTO> restaurantesDTO = assembler.toCollectionDTO(restaurantes);
		return ResponseEntity.ok(restaurantesDTO);
	}
	
	@GetMapping("/{restauranteId}")
	public ResponseEntity<RestauranteDTO> buscarPorId(@PathVariable(name = "restauranteId") Long id) {
		Restaurante restaurante = restauranteService.BuscarOuFalhar(id);
		RestauranteDTO restauranteDTO = assembler.toRestauranteDTO(restaurante);
		return ResponseEntity.ok(restauranteDTO);
	}
	
	@PostMapping
	public ResponseEntity<RestauranteDTO> adicionar(@RequestBody @Valid RestauranteInputDTO restauranteInput) {
		try {
			var restaurante = disassembler.toDomainObject(restauranteInput);
			restaurante = restauranteService.adicionar(restaurante);
			URI uri = ServletUriComponentsBuilder
					.fromCurrentRequest()
					.path("/{id}")
					.buildAndExpand(restaurante.getId())
					.toUri();
			var restauranteDTO = assembler.toRestauranteDTO(restaurante);
			return ResponseEntity.created(uri).body(restauranteDTO);
		} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException exception) {
			throw new NegocioException(exception.getMessage(), exception);
		}
		
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<RestauranteDTO> atualizar(@PathVariable("id") Long restauranteId, @RequestBody @Valid RestauranteInputDTO restauranteInput) {
		
		try {
			var restauranteAtual = restauranteService.BuscarOuFalhar(restauranteId);
			disassembler.copyToDomainObject(restauranteInput, restauranteAtual);
			
			var restauranteDTO = assembler.toRestauranteDTO(restauranteService.atualizar(restauranteAtual));
			return ResponseEntity.ok(restauranteDTO);
		} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException exception) {
			
			throw new NegocioException(exception.getMessage(), exception);
		}
	
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<?> atualizarParcial(@PathVariable Long id, @RequestBody Map<String, Object> campos, HttpServletRequest request) {
		
		var restauranteEntidade = restauranteService.BuscarOuFalhar(id);
		
		merge(campos, restauranteEntidade, request);
		validate(restauranteEntidade, "restaurante");
		restauranteEntidade = restauranteService.atualizar(restauranteEntidade);
		var restauranteDTO = assembler.toRestauranteDTO(restauranteEntidade);
		return ResponseEntity.ok(restauranteDTO);	
	}

	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		restauranteService.deletar(id);
		return ResponseEntity.noContent().build();
	}
	
	
	@GetMapping("/listar-por-parametros")
	public ResponseEntity<List<RestauranteDTO>> listarTest(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {
		var restaurantes = restauranteRepository.find(nome, taxaInicial, taxaFinal);
		var restaurantesDTO = assembler.toCollectionDTO(restaurantes);
		return ResponseEntity.ok(restaurantesDTO);
	}
	
	@GetMapping("/com-frete-gratis")
	public ResponseEntity<List<RestauranteDTO>> listarComFreteGratis(String nome) {
		var restaurantes = restauranteRepository.listarComFreteGratis(nome);
		var restaurantesDTO = assembler.toCollectionDTO(restaurantes);
		return ResponseEntity.ok(restaurantesDTO);
	}
	
	@PutMapping("/${restauranteId}/ativo")
	public ResponseEntity<Void> ativarRestaurante(@PathVariable Long restauranteId) {
		restauranteService.ativar(restauranteId);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/${restauranteId}/inativo")
	public ResponseEntity<Void> inativarRestaurante(@PathVariable Long restauranteId) {
		restauranteService.inativar(restauranteId);
		return ResponseEntity.noContent().build();
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
