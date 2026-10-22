package com.blue.bluefood.infrastructure.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.blue.bluefood.domain.model.FotoProduto;
import com.blue.bluefood.domain.repository.ProdutoRepositoryQueries;

@Repository
public class ProdutoRepositoryImpl implements ProdutoRepositoryQueries {
	
	@PersistenceContext
	private EntityManager manager;
	
	@Transactional
	@Override
	public FotoProduto save(FotoProduto fotoProduto) {
		return manager.merge(fotoProduto);
	}

	@Transactional
	@Override
	public void delete(FotoProduto fotoProduto) {
		manager.remove(fotoProduto);
		
	}

}
