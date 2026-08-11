package com.blue.bluefood.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blue.bluefood.domain.model.Cozinha;

@Repository
public interface CozinhaRepository extends JpaRepository<Cozinha, Long> {
	
	List<Cozinha> todas();
	List<Cozinha> consultarPorNome(String nome);
	Cozinha buscarPorId(Long id);
	Cozinha salvar(Cozinha cozinha);
	
	void remover(Long id);
	
}
