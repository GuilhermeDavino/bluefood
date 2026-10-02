package com.blue.bluefood.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blue.bluefood.api.model.PedidoResumoDTO;
import com.blue.bluefood.domain.model.Pedido;

@Component
public class PedidoResumoDTOAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public PedidoResumoDTO toPedidoResumoDTO(Pedido pedido) {
		return modelMapper.map(pedido, PedidoResumoDTO.class);
	}
	
	public List<PedidoResumoDTO> toCollectionDTO(Collection<Pedido> pedidos) {
		return pedidos.stream()
				.map(pedido -> toPedidoResumoDTO(pedido))
				.collect(Collectors.toList());
	}
}
