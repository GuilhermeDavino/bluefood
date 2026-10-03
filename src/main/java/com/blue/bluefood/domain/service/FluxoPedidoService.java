package com.blue.bluefood.domain.service;

import java.time.OffsetDateTime;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blue.bluefood.domain.exception.NegocioException;
import com.blue.bluefood.domain.model.StatusPedido;

@Service
public class FluxoPedidoService {

	@Autowired
	private PedidoService pedidoService;
	
	@Transactional
	public void confirmar(Long pedidoId) {
		var pedido = pedidoService.buscarOuFalhar(pedidoId);
		
		if (!pedido.getStatus().equals(StatusPedido.CRIADO)) {
			throw new NegocioException(
					String.format("Status do pedido de id %d não pode ser"
							+ " alterado de %s para %s", pedido.getId(), 
							pedido.getStatus().getDescricao(), 
							StatusPedido.CONFIRMADO.getDescricao()));
		}
		
		pedido.setStatus(StatusPedido.CONFIRMADO);
		pedido.setDataConfirmacao(OffsetDateTime.now());
	}
	
	@Transactional
	public void entregar(Long pedidoId) {
		var pedido = pedidoService.buscarOuFalhar(pedidoId);
		if (!pedido.getStatus().equals(StatusPedido.CONFIRMADO)) {
			
			throw new NegocioException(
					String.format("Status do pedido de id %d não pode ser"
							+ " alterado de %s para %s", pedido.getId(), 
							pedido.getStatus().getDescricao(), 
							StatusPedido.ENTREGUE.getDescricao()));
			
		}
		
		pedido.setStatus(StatusPedido.ENTREGUE);
		pedido.setDataEntrega(OffsetDateTime.now());
	}
	
	@Transactional
	public void cancelar(Long pedidoId) {
		var pedido = pedidoService.buscarOuFalhar(pedidoId);
		if (!pedido.getStatus().equals(StatusPedido.CRIADO)) {
			throw new NegocioException(
					String.format("Status do pedido de id %d não pode ser"
							+ " alterado de %s para %s", pedido.getId(), 
							pedido.getStatus().getDescricao(), 
							StatusPedido.CANCELADO.getDescricao()));
		}
		
		pedido.setStatus(StatusPedido.CANCELADO);
		pedido.setDataCancelamento(OffsetDateTime.now());
	}
}
