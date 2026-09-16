package com.blue.bluefood.domain.exception;

public class FormaPagamentoNaoEncontrada extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public FormaPagamentoNaoEncontrada(Long formaPagamentoId) {
		super(String.format("A forma de pagamento de id %d não foi encontrada", formaPagamentoId));
	}
	
	public FormaPagamentoNaoEncontrada(Long formaPagamentoId, Throwable exception) {
		super(String.format("A forma de pagamento de id %d não foi encontrada", formaPagamentoId), exception);
	}

}
