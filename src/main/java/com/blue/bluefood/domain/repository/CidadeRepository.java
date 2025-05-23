package com.blue.bluefood.domain.repository;

import java.util.List;

import com.blue.bluefood.domain.model.Cidade;

public interface CidadeRepository {
	
	List<Cidade> listar();
	
	Cidade buscarPorId(Long id);
	
	Cidade salvar(Cidade cidade);
	
	void remover(Long id);
	
}
