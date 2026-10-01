package com.blue.bluefood.domain.exception;

public class PermissaoNaoEncontrada extends EntidadeNaoEncontradaException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PermissaoNaoEncontrada(Long permissaoId) {
		super(String.format("A permissão de id %d não foi encontrada", permissaoId));
		
	}
	
	public PermissaoNaoEncontrada(Long permissaoId, Throwable exception) {
		super(String.format("A permissão de id %d não foi encontrada", permissaoId), exception);	
	}
	
	public PermissaoNaoEncontrada(Long grupoId, Long permissaoId) {
		super(String.format("Não existe uma permissão de id %d para o grupo de id %d", permissaoId, grupoId));
		
	}
	
}
