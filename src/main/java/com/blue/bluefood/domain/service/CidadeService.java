package com.blue.bluefood.domain.service;

import org.springframework.beans.BeanUtils;
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
	
	public Cidade atualizar(Long id, Cidade cidade) {
		Long estadoId = cidade.getEstado().getId();
		Estado estado = estadoRepository.buscarPorId(estadoId);
		if(estado == null) throw new EntidadeNaoEncontradaException(
				String.format("O estado de id %d não existe ou não foi encontrado!", estadoId));
		Cidade cidadeEntity = cidadeRepository.buscarPorId(id);
		if(cidadeEntity == null) throw new EntidadeNaoEncontradaException(
				String.format("A cidade de id %d não existe ou não foi encontrado!", id));
		BeanUtils.copyProperties(cidade, cidadeEntity, "id");
		return cidadeRepository.salvar(cidadeEntity);
		
	}
}
