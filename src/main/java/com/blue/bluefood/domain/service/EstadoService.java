package com.blue.bluefood.domain.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.blue.bluefood.domain.exception.EntidadeEmUsoException;
import com.blue.bluefood.domain.exception.EstadoNaoEncontradoException;
import com.blue.bluefood.domain.model.Estado;
import com.blue.bluefood.domain.repository.EstadoRepository;

@Service
public class EstadoService {
	
	private static final String ESTADO_EM_USO = "O estado de id %d está em uso";
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	public Estado adicionar(Estado estado) {
		estado.setId(null);
		estado = estadoRepository.salvar(estado);
		return estado;
	}
	
	public Estado atualizar(Long estadoId, Estado estadoNovo) {
		
		Estado estado = buscarOuFalhar(estadoId);
		BeanUtils.copyProperties(estadoNovo, estado, "id");
		estadoNovo = estadoRepository.salvar(estado);
		return estadoNovo;
	} 
	
	public void deletar(Long estadoId) {
		
		try {
			estadoRepository.remover(estadoId);
		} catch (EmptyResultDataAccessException exception) {
			throw new EstadoNaoEncontradoException(estadoId, exception);	
		} catch(DataIntegrityViolationException exception) {
			throw new EntidadeEmUsoException(String.format(ESTADO_EM_USO, estadoId));
		}
		
	}
	
	public Estado buscarOuFalhar(Long estadoId) {
		return estadoRepository.findById(estadoId)
				.orElseThrow(() -> 
				new EstadoNaoEncontradoException(estadoId));
	}
}
