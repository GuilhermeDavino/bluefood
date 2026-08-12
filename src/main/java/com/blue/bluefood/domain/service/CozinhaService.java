package com.blue.bluefood.domain.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.blue.bluefood.domain.exception.EntidadeNaoEncontradaException;
import com.blue.bluefood.domain.exception.CozinhaNaoEncontradaException;
import com.blue.bluefood.domain.exception.EntidadeEmUsoException;
import com.blue.bluefood.domain.model.Cozinha;
import com.blue.bluefood.domain.repository.CozinhaRepository;

@Service
public class CozinhaService {
	
	private static final String MSG_COZINHA_EM_USO = "Cozinha de código %d não pode ser removida,"
			+ " pois está em uso";
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	public List<Cozinha> buscarTodas() {
		return cozinhaRepository.findAll();
	}
	
	public Cozinha salvar(Cozinha cozinha) {
		return cozinhaRepository.salvar(cozinha);
	}
	
	public Cozinha atualizar(Long cozinhaId, Cozinha cozinhaNova) {
		var cozinhaAtual = buscarOuFalhar(cozinhaId);
		BeanUtils.copyProperties(cozinhaNova, cozinhaAtual, "id");
		var cozinhaSalva = cozinhaRepository.salvar(cozinhaAtual);
		return cozinhaSalva;
	}
	
	public void remover(Long cozinhaId) {
		try {
			cozinhaRepository.remover(cozinhaId);
		} catch (EmptyResultDataAccessException exception) { 
			throw new CozinhaNaoEncontradaException(cozinhaId, exception);
			
		} catch (DataIntegrityViolationException exception) {
			
			throw new EntidadeEmUsoException(String.format(MSG_COZINHA_EM_USO, cozinhaId));
		}
		
	}
	
	public Cozinha buscarOuFalhar(Long cozinhaId) {
		return cozinhaRepository.findById(cozinhaId)
				.orElseThrow(() -> 
				new CozinhaNaoEncontradaException(cozinhaId));
	}
	
}
