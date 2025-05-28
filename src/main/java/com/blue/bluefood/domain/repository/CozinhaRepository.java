package com.blue.bluefood.domain.repository;

import java.util.List;

import org.springframework.stereotype.Component;

import com.blue.bluefood.domain.model.Cozinha;

@Component
public interface CozinhaRepository {
	
	List<Cozinha> todas();
	List<Cozinha> consultarPorNome(String nome);
	Cozinha buscarPorId(Long id);
	Cozinha salvar(Cozinha cozinha);
	
	void remover(Long id);
	
}
