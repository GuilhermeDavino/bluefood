package com.blue.bluefood.domain.exception;

import org.springframework.http.HttpStatus;

public class EstadoNaoEncontradoException extends EntidadeNaoEncontradaException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EstadoNaoEncontradoException(HttpStatus status, String mensagem) {
		super(status, mensagem);
	}
	
	public EstadoNaoEncontradoException(String mensagem) {
		this(HttpStatus.NOT_FOUND, mensagem);
	}

}
