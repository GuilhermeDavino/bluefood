package com.blue.bluefood.domain.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.blue.bluefood.domain.exception.EntidadeEmUsoException;
import com.blue.bluefood.domain.exception.PedidoNaoEncontrado;
import com.blue.bluefood.domain.model.Pedido;
import com.blue.bluefood.domain.repository.PedidoRepository;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Transactional
	public List<Pedido> listarPedidos() {
		return pedidoRepository.findAll();
	}
	
	@Transactional
	public Pedido buscarOuFalhar(String codigoPedido) {
		return pedidoRepository.findByCodigo(codigoPedido)
				.orElseThrow(() -> new PedidoNaoEncontrado(codigoPedido));
	}
	
	@Transactional
	public Pedido adicionar(Pedido pedido) {
		return pedidoRepository.save(pedido);
	}
	
	@Transactional
	public Pedido atualizar(Pedido pedido) {
		return pedidoRepository.save(pedido);
	}
	
	@Transactional
	public void deletar(Long pedidoId) {
		try {
			pedidoRepository.deleteById(pedidoId);
		} catch (EmptyResultDataAccessException exception) {
			throw new PedidoNaoEncontrado(pedidoId, exception);
		} catch (DataIntegrityViolationException exception) {
			throw new EntidadeEmUsoException(String.format("O pedido de id %s está em uso", pedidoId));
		}
		
	}
}
