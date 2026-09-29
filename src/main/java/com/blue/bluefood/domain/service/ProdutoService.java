package com.blue.bluefood.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blue.bluefood.domain.exception.ProdutoNaoEncontrado;
import com.blue.bluefood.domain.model.Produto;
import com.blue.bluefood.domain.repository.ProdutoRepository;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository repository;
	
	@Transactional
	public Produto buscarOuFalhar(Long produtoId) {
		return repository.findById(produtoId).orElseThrow(
				() -> new ProdutoNaoEncontrado(produtoId));
	}
}
