package com.blue.bluefood.domain.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blue.bluefood.domain.exception.EntidadeNaoEncontradaException;
import com.blue.bluefood.domain.model.Estado;
import com.blue.bluefood.domain.repository.EstadoRepository;

@Service
public class EstadoService {
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	public Estado adicionar(Estado estado) {
		estado.setId(null);
		estado = estadoRepository.salvar(estado);
		return estado;
	}
	
	public Estado atualizar(Long id, Estado estadoNovo) {
		Estado estado = estadoRepository.buscarPorId(id);
		if(estado == null) throw new EntidadeNaoEncontradaException(
				String.format("O estado de id %d não foi encontrado ou não existe!", id));
		BeanUtils.copyProperties(estadoNovo, estado, "id");
		estadoNovo = estadoRepository.salvar(estado);
		return estadoNovo;
		
		
	}
}
