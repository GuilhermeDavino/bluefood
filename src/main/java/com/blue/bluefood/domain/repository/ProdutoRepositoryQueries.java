package com.blue.bluefood.domain.repository;

import com.blue.bluefood.domain.model.FotoProduto;

public interface ProdutoRepositoryQueries {
	
	FotoProduto save(FotoProduto fotoProduto);
	
	void delete(FotoProduto fotoProduto);
}
