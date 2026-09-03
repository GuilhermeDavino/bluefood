package com.blue.bluefood.domain.service;

import java.lang.reflect.Field;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.blue.bluefood.domain.exception.EntidadeEmUsoException;
import com.blue.bluefood.domain.exception.RestauranteNaoEncontradoException;
import com.blue.bluefood.domain.model.Cozinha;
import com.blue.bluefood.domain.model.Restaurante;
import com.blue.bluefood.domain.repository.RestauranteRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RestauranteService {
	
	private static final String MSG_RESTAURANTE_EM_USO = "Restaurante de código %d não pode ser removido,"
			+ " pois está em uso";

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CozinhaService cozinhaService;
	
	public Restaurante adicionar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		@SuppressWarnings("unused")
		Cozinha cozinha = cozinhaService.buscarOuFalhar(cozinhaId);
		restaurante.setId(null);
		restaurante = restauranteRepository.salvar(restaurante);
		return restaurante;
	}
	
	public Restaurante atualizar(Restaurante restaurante) {
		
		Long cozinhaId = restaurante.getCozinha().getId();
		@SuppressWarnings("unused")
		Cozinha cozinha = cozinhaService.buscarOuFalhar(cozinhaId);
		Restaurante restauranteNovo = restauranteRepository.salvar(restaurante);
		System.out.println(restauranteNovo.getCozinha().getNome());
		return restauranteNovo;
	}
	
	public void deletar(Long restauranteId) {
		try {
			restauranteRepository.remover(restauranteId);
		} catch (EmptyResultDataAccessException exception) { 
			throw new RestauranteNaoEncontradoException(restauranteId, exception);
			
		} catch (DataIntegrityViolationException exception) {
			
			throw new EntidadeEmUsoException(String.format(MSG_RESTAURANTE_EM_USO, restauranteId));
		}
		
	}
	
	public Restaurante atualizarParcial(Long restauranteId, Map<String, Object> campos, HttpServletRequest request) {
		Restaurante restauranteEntity = BuscarOuFalhar(restauranteId);
		merge(campos, restauranteEntity, request);
		restauranteEntity = atualizar(restauranteEntity);
		return restauranteEntity;
		
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
	
	public Restaurante BuscarOuFalhar(Long restauranteId) {
		return restauranteRepository.findById(restauranteId).
				orElseThrow(() -> 
				new RestauranteNaoEncontradoException(restauranteId));
		
	}
	
}
