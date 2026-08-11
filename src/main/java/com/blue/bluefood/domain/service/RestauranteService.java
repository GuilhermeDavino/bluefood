package com.blue.bluefood.domain.service;

import java.lang.reflect.Field;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.blue.bluefood.domain.exception.EntidadeEmUsoException;
import com.blue.bluefood.domain.exception.EntidadeNaoEncontradaException;
import com.blue.bluefood.domain.model.Cozinha;
import com.blue.bluefood.domain.model.Restaurante;
import com.blue.bluefood.domain.repository.RestauranteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RestauranteService {
	private static final String MSG_RESTAURANTE_EM_USO = "Restaurante de código %d não pode ser removido,"
			+ " pois está em uso";

	private static final String MSG_RESTAURANTE_NAO_ENCONTRADO = "o restaurante de id %d não foi encontrada";

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
		return restauranteRepository.salvar(restaurante);
	}
	
	public void deletar(Long id) {
		try {
			restauranteRepository.remover(id);
		} catch (EmptyResultDataAccessException exception) { 
			throw new EntidadeNaoEncontradaException(String.format(MSG_RESTAURANTE_NAO_ENCONTRADO, id));
			
		} catch (DataIntegrityViolationException exception) {
			
			throw new EntidadeEmUsoException(String.format(MSG_RESTAURANTE_EM_USO, id));
		}
		
	}
	
	public Restaurante atualizarParcial(Long restauranteId, Map<String, Object> campos) {
		Restaurante restauranteEntity = BuscarOuFalhar(restauranteId);
		merge(campos, restauranteEntity);
		restauranteEntity = atualizar(restauranteEntity);
		return restauranteEntity;
		
	}
	
	private void merge(Map<String, Object> campos, Restaurante restauranteDestino) {
		ObjectMapper objectMapper = new ObjectMapper();
		Restaurante restauranteNovo = objectMapper.convertValue(campos, Restaurante.class);
		
		campos.forEach((chave, valor) -> {
			Field field = ReflectionUtils.findField(Restaurante.class, chave);
			field.setAccessible(true);
			Object novoValor = ReflectionUtils.getField(field, restauranteNovo);
			ReflectionUtils.setField(field, restauranteDestino, novoValor);
		});
	}
	
	public Restaurante BuscarOuFalhar(Long restauranteId) {
		return restauranteRepository.findById(restauranteId).
				orElseThrow(() -> 
				new EntidadeNaoEncontradaException(
						String.format(MSG_RESTAURANTE_NAO_ENCONTRADO,
								restauranteId)));
		
	}
	
}
