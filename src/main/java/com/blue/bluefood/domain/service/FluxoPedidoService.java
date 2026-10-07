package com.blue.bluefood.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FluxoPedidoService {

	@Autowired
	private PedidoService pedidoService;
	
	@Transactional
	public void confirmar(Long pedidoId) {
		var pedido = pedidoService.buscarOuFalhar(pedidoId);
		pedido.confirmar();
	}
	
	@Transactional
	public void entregar(Long pedidoId) {
		var pedido = pedidoService.buscarOuFalhar(pedidoId);
		pedido.entregar();
	}
	
	@Transactional
	public void cancelar(Long pedidoId) {
		var pedido = pedidoService.buscarOuFalhar(pedidoId);
		pedido.cancelar();
	}
}
