package com.blue.bluefood.domain.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.blue.bluefood.domain.exception.EntidadeEmUsoException;
import com.blue.bluefood.domain.model.Estado;
import com.blue.bluefood.domain.repository.EstadoRepository;

@Service
public class EstadoService {
	
	private static final String ESTADO_EM_USO = "O estado %s está em uso";
	
	private static final String MGS_ESTADO_NAO_ENCONTRADO = "O estado de id %d não foi encontrado ou não existe!";
	
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
		Estado estado = buscarOuFalhar(estadoId);
		try {
			estadoRepository.remover(estadoId);
		} catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(ESTADO_EM_USO, estado.getNome()));
		}
		
	}
	
	public Estado buscarOuFalhar(Long estadoId) {
		return estadoRepository.findById(estadoId)
				.orElseThrow(() -> 
				new EntidadeEmUsoException(String.format(MGS_ESTADO_NAO_ENCONTRADO, estadoId)));
	}
}
