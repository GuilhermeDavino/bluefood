package com.blue.bluefood.domain.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.blue.bluefood.domain.exception.EntidadeEmUsoException;
import com.blue.bluefood.domain.exception.EntidadeNaoEncontradaException;
import com.blue.bluefood.domain.model.Cozinha;
import com.blue.bluefood.domain.model.Restaurante;
import com.blue.bluefood.domain.repository.CozinhaRepository;
import com.blue.bluefood.domain.repository.RestauranteRepository;

@Service
public class RestauranteService {
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	public Restaurante adicionar(Restaurante restaurante) {
		
		Long cozinhaId = restaurante.getCozinha().getId();
		Cozinha cozinha = cozinhaRepository.buscarPorId(cozinhaId);
		if(cozinha == null) throw new EntidadeNaoEncontradaException(String.format("Cozinha com id %d não encontrada", cozinhaId));
		restaurante.setId(null);
		restaurante = restauranteRepository.salvar(restaurante);
		return restaurante;
	}
	
	public Restaurante atualizar(Long id, Restaurante restauranteNovo) {
		Long cozinhaId = restauranteNovo.getCozinha().getId();
		Cozinha cozinha = cozinhaRepository.buscarPorId(cozinhaId);
		if(cozinha == null) throw new EntidadeNaoEncontradaException(String.format("Cozinha com id %d não encontrada", cozinhaId));
		
		Restaurante restaurante = restauranteRepository.buscarPorId(id);
		BeanUtils.copyProperties(restauranteNovo, restaurante, "id");
		restauranteNovo = restauranteRepository.salvar(restaurante);
		return restauranteNovo;
	}
	
	public void deletar(Long id) {
		try {
			restauranteRepository.remover(id);
		} catch (EmptyResultDataAccessException exception) { 
			throw new EntidadeNaoEncontradaException(String.format("A entidade de id %d não foi encontrada", id));
			
		} catch (DataIntegrityViolationException exception) {
			
			throw new EntidadeEmUsoException(String.format("Restaurante de código %d não pode ser removido,"
					+ " pois está em uso", id));
		}
		
	}
	
}
