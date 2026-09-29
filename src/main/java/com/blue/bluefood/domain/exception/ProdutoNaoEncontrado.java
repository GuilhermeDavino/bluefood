package com.blue.bluefood.domain.exception;

public class ProdutoNaoEncontrado extends EntidadeNaoEncontradaException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProdutoNaoEncontrado(Long produtoId) {
		super(String.format("O produto de id %d não foi encontrado", produtoId));
	}
	
	public ProdutoNaoEncontrado(Long produtoId, Throwable exception) {
		super(String.format("O produto de id %d não foi encontrado", produtoId), exception);
	}
	
	public ProdutoNaoEncontrado(Long produtoId, Long restauranteId) {
		super(String.format("Não existe um produto de id %d para o restaurante de id %d", produtoId, restauranteId));
	}

}
