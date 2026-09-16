package com.blue.bluefood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blue.bluefood.api.model.CidadeInputDTO;
import com.blue.bluefood.domain.model.Cidade;

@Component
public class CidadeInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Cidade toDomainObject(CidadeInputDTO cidadeDTO) {
		return modelMapper.map(cidadeDTO, Cidade.class);
	}
	
	public void copyToDomainObject(CidadeInputDTO cidadeInput, Cidade cidade) {
		modelMapper.map(cidadeInput, cidade);
	}
}
