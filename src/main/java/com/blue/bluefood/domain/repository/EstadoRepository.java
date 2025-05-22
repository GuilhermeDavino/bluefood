package com.blue.bluefood.domain.repository;

import java.util.List;

import com.blue.bluefood.domain.model.Estado;

public interface EstadoRepository {
	
	List<Estado> listar();
	
	Estado buscarPorId(Long id);
	
	Estado salvar(Estado estado);
	
	void remover(Long id);
	
}
