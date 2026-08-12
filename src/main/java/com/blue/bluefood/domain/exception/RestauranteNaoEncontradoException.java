package com.blue.bluefood.domain.exception;

public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException {
	
	private static final long serialVersionUID = 1L;

	public RestauranteNaoEncontradoException(Long restauranteId) {
		super(String.format("O restaurante de id %d não foi encontrado", restauranteId));
	}
	
	public RestauranteNaoEncontradoException(Long restauranteId, Throwable exception) {
		super(String.format("O restaurante de id %d não foi encontrado", restauranteId), exception);
	}
}
