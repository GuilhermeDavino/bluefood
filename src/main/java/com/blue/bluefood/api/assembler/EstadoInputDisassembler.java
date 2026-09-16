package com.blue.bluefood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blue.bluefood.api.model.EstadoDTO;
import com.blue.bluefood.domain.model.Estado;

@Component
public class EstadoInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Estado toDomainObject(EstadoDTO estadoDTO) {
		return modelMapper.map(estadoDTO, Estado.class);
	}
	
	public void copyToDomainObject(EstadoDTO estadoInput, Estado estado) {
		modelMapper.map(estadoInput, estado);
	}
}
