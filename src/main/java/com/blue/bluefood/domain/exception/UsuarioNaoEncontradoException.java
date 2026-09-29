package com.blue.bluefood.domain.exception;

public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String MSG_USUARIO_NAO_ENCONTRADO = "O usuário de id %d não foi encontrado";

	public UsuarioNaoEncontradoException(Long usuarioId) {
		super(String.format(MSG_USUARIO_NAO_ENCONTRADO, usuarioId));
	}
	
	public UsuarioNaoEncontradoException(Long usuarioId, Throwable exception) {
		super(String.format(MSG_USUARIO_NAO_ENCONTRADO, usuarioId), exception);
	}

}
