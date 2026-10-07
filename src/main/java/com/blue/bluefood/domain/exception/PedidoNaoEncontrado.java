package com.blue.bluefood.domain.exception;

public class PedidoNaoEncontrado extends EntidadeNaoEncontradaException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PedidoNaoEncontrado(Long pedidoId) {
		super(String.format("O pedido de id %d não foi encontrado", pedidoId));
		
	}
	
	public PedidoNaoEncontrado(String codigoPedido) {
		super(String.format("O pedido de id %s não foi encontrado", codigoPedido));
		
	}
	
	public PedidoNaoEncontrado(Long pedidoId, Throwable exception) {
		super(String.format("O pedido de id %d não foi encontrado", pedidoId), exception);
	}

}
