package com.blue.bluefood.domain.exception;

public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GrupoNaoEncontradoException(Long grupoId) {
		super(String.format("O grupo de de id %d não foi encontrado", grupoId));
	}

	public GrupoNaoEncontradoException(Long grupoId, Throwable exception) {
		super(String.format("O grupo de de id %d não foi encontrado", grupoId), exception);
	}
	
	

}
