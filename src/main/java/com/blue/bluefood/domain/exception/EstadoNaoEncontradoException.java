package com.blue.bluefood.domain.exception;

public class EstadoNaoEncontradoException extends EntidadeNaoEncontradaException {
	
	
	private static final long serialVersionUID = 1L;
	
	
	public EstadoNaoEncontradoException(Long id) {
		super(String.format("Não existe um estado cadastro de id %d", id));
	}

	public EstadoNaoEncontradoException(Long id, Throwable exception) {
		super(String.format("Não existe um estado cadastro de id %d", id), exception);
	}
		

}
