package com.blue.bluefood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blue.bluefood.domain.exception.EntidadeNaoEncontradaException;
import com.blue.bluefood.domain.model.Cidade;
import com.blue.bluefood.domain.model.Estado;
import com.blue.bluefood.domain.repository.CidadeRepository;
import com.blue.bluefood.domain.repository.EstadoRepository;

@Service
public class CidadeService {
	
	@Autowired	
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	
	public Cidade adicionar(Cidade cidade) {
		Long estadoId = cidade.getEstado().getId();
		Estado estado = estadoRepository.buscarPorId(estadoId);
		if(estado == null) throw new EntidadeNaoEncontradaException(
				String.format("O estado de id %d não existe ou não foi encontrado!", estadoId));
		
		cidade = cidadeRepository.salvar(cidade);
		return cidade;
		
	}
}
