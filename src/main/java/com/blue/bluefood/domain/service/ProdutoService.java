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
	
	@Autowired
	private RestauranteService restauranteService;
	
	@Transactional
	public Produto buscarOuFalhar(Long produtoId) {
		return repository.findById(produtoId).orElseThrow(
				() -> new ProdutoNaoEncontrado(produtoId));
	}
	
	@Transactional
	public Produto buscarProdutoPorRestaurante(Long produtoId, Long restauranteId) {
		return repository.findById(restauranteId, produtoId).orElseThrow(
				() -> new ProdutoNaoEncontrado(produtoId, restauranteId));
	}
	
	@Transactional
	public Produto adicionar(Long restauranteId, Produto produtoInput) {
		var restaurante = restauranteService.buscarOuFalhar(restauranteId);
		produtoInput.setRestaurante(restaurante);
		return repository.save(produtoInput);
	}
	
	@Transactional
	public Produto atualizar(Produto produtoInput) {
		return repository.save(produtoInput);
	}
	
}
