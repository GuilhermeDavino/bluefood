package com.blue.bluefood.domain.service;

import org.springframework.stereotype.Service;

import com.blue.bluefood.domain.model.Estado;
import com.blue.bluefood.domain.repository.EstadoRepository;

@Service
public class EstadoService {
	
	private EstadoRepository estadoRepository;
	
	public Estado adicionar(Estado estado) {
		estado.setId(null);
		estado = estadoRepository.salvar(estado);
		return estado;
	}
}
