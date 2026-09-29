package com.blue.bluefood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blue.bluefood.api.model.GrupoDTO;
import com.blue.bluefood.domain.model.Grupo;

@Component
public class GrupoInputDisassembler {
	@Autowired
	private ModelMapper modelMapper;
	
	public Grupo toDomainObject(GrupoDTO grupoDTO) {
		return modelMapper.map(grupoDTO, Grupo.class);
	}
	
	
}
