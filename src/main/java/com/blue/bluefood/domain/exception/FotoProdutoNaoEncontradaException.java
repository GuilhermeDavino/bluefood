package com.blue.bluefood.domain.exception;

public class FotoProdutoNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public FotoProdutoNaoEncontradaException(Long produtoId, Long restauranteId) {
		super(String.format("O produto de id %d do restaurante de id %d não tem foto", produtoId, restauranteId));
	}

}
