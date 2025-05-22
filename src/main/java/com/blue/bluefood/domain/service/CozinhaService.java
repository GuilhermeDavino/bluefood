package com.blue.bluefood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.blue.bluefood.domain.exception.EntidadeNaoEncontradaException;
import com.blue.bluefood.domain.exception.EntidadeEmUsoException;
import com.blue.bluefood.domain.model.Cozinha;
import com.blue.bluefood.domain.repository.CozinhaRepository;

@Service
public class CozinhaService {
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	public Cozinha salvar(Cozinha cozinha) {
		return cozinhaRepository.salvar(cozinha);
	}
	
	public void remover(Long id) {
		try {
			cozinhaRepository.remover(id);
		} catch (EmptyResultDataAccessException exception) { 
			throw new EntidadeNaoEncontradaException(String.format("A entidade de id %d não foi encontrada", id));
			
		} catch (DataIntegrityViolationException exception) {
			
			throw new EntidadeEmUsoException(String.format("Cozinha de código %d não pode ser removida,"
					+ " pois está em uso", id));
		}
		
	}
	
}
