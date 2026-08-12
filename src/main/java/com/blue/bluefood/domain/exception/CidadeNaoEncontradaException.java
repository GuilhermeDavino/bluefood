package com.blue.bluefood.domain.exception;

public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public CidadeNaoEncontradaException(Long cidadeId) {
		super(String.format("cidade de id %d não encontrada", cidadeId));
	}
	
	public CidadeNaoEncontradaException(Long cidadeId, Throwable exception) {
		super(String.format("cidade de id %d não encontrada", cidadeId), exception);
	}
	
}
