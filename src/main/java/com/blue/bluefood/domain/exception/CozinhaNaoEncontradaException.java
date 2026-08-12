package com.blue.bluefood.domain.exception;

public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException {
	
	private static final long serialVersionUID = 1L;

	public CozinhaNaoEncontradaException(Long cozinhaId) {
		super(String.format("A cozinha de id %d não foi encontrada", cozinhaId));
	}
	
	public CozinhaNaoEncontradaException(Long cozinhaId, Throwable exception) {
		super(String.format("A cozinha de id %d não foi encontrada", cozinhaId), exception);
	}
}
