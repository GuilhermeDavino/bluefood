package com.blue.bluefood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.blue.bluefood.domain.exception.CidadeNaoEncontradaException;
import com.blue.bluefood.domain.exception.EntidadeEmUsoException;
import com.blue.bluefood.domain.exception.EntidadeNaoEncontradaException;
import com.blue.bluefood.domain.model.Cidade;
import com.blue.bluefood.domain.model.Estado;
import com.blue.bluefood.domain.repository.CidadeRepository;

@Service
public class CidadeService {
	
	private static final String MSG_CIDADE_EM_USO = "Cidade de código %d não pode ser removido,"
			+ " pois está em uso";

	@Autowired	
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoService estadoService;
	
	
	public Cidade adicionar(Cidade cidade) {
		Long estadoId = cidade.getEstado().getId();
		@SuppressWarnings("unused")
		Estado estado = estadoService.buscarOuFalhar(estadoId);
		cidade = cidadeRepository.salvar(cidade);
		return cidade;
	}
	
	public Cidade atualizar(Cidade cidade) {
		Long estadoId = cidade.getEstado().getId();
		@SuppressWarnings("unused")
		var estado = estadoService.buscarOuFalhar(estadoId);
		return cidadeRepository.salvar(cidade);
	}
	
	public void remover(Long cidadeId) {
		try {
			cidadeRepository.remover(cidadeId);
		} catch(EntidadeNaoEncontradaException e) {
			throw new CidadeNaoEncontradaException(cidadeId, e);
		} catch (DataIntegrityViolationException exception) {
			throw new EntidadeEmUsoException(String.format(MSG_CIDADE_EM_USO, cidadeId));
		}
		
	}
	
	public Cidade buscarOuFalhar(Long cidadeId) {
		return cidadeRepository.findById(cidadeId)
				.orElseThrow(() -> 
				new CidadeNaoEncontradaException(cidadeId));
	}
}
