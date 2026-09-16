package com.blue.bluefood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blue.bluefood.api.model.EstadoDTO;
import com.blue.bluefood.domain.model.Estado;

@Component
public class EstadoDTOAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public EstadoDTO toEstadoDTO(Estado estado) {
		return modelMapper.map(estado, EstadoDTO.class);
	}
	
	public List<EstadoDTO> toCollectionDTO(List<Estado> estados) {
		return estados.stream().map(x -> toEstadoDTO(x)).collect(Collectors.toList());
	}

}
