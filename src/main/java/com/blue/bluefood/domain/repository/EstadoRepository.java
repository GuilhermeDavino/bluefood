package com.blue.bluefood.domain.repository;

import java.util.List;

import com.blue.bluefood.domain.model.Estado;

public interface EstadoRepository {
	
	List<Estado> listar();
	
	Estado porId(Long id);
	
	Estado adicionar(Estado estado);
	
	void remover(Estado estado);
	
}
