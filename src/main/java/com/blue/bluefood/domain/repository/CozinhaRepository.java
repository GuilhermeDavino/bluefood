package com.blue.bluefood.domain.repository;

import java.util.List;

import com.blue.bluefood.domain.model.Cozinha;

public interface CozinhaRepository {
	
	List<Cozinha> todas();
	
	Cozinha porId(Long id);
	
	Cozinha salvar(Cozinha cozinha);
	
	void remover(Long id);
	
}
