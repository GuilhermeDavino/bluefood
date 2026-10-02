package com.blue.bluefood.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blue.bluefood.api.model.input.PedidoInputDTO;
import com.blue.bluefood.domain.model.Pedido;

@Component
public class PedidoInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Pedido toDomainObject(PedidoInputDTO pedidoInput) {
		return modelMapper.map(pedidoInput, Pedido.class);
	}
	
	public List<Pedido> toDomainCollection(Collection<PedidoInputDTO> pedidosInput) {
		return pedidosInput.stream()
				.map(pedidoInput -> toDomainObject(pedidoInput))
				.collect(Collectors.toList());
	}
}
