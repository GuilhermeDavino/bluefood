package com.blue.bluefood.domain.repository;

import java.util.List;

import com.blue.bluefood.domain.model.Restaurante;

public interface RestauranteRepository {
	
	List<Restaurante> todos();
	
	Restaurante porId(Long id);
	
	Restaurante salvar(Restaurante restaurante);
	
	void remover(Restaurante restaurante);
}
