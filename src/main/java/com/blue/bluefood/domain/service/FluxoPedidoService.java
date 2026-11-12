package com.blue.bluefood.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blue.bluefood.domain.repository.PedidoRepository;

@Service
public class FluxoPedidoService {

	@Autowired
	private PedidoService pedidoService;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Transactional
	public void confirmar(String codigoPedido) {
		var pedido = pedidoService.buscarOuFalhar(codigoPedido);
		pedido.confirmar();
		pedidoRepository.save(pedido);
		
	}
	
	@Transactional
	public void entregar(String codigoPedido) {
		var pedido = pedidoService.buscarOuFalhar(codigoPedido);
		pedido.entregar();
	}
	
	@Transactional
	public void cancelar(String codigoPedido) {
		var pedido = pedidoService.buscarOuFalhar(codigoPedido);
		pedido.cancelar();
	}
}
