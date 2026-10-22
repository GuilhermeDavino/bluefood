package com.blue.bluefood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blue.bluefood.api.model.FotoProdutoDTO;
import com.blue.bluefood.domain.model.FotoProduto;

@Component
public class FotoProdutoAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public FotoProdutoDTO toFotoProdutoDTO(FotoProduto fotoProduto) {
		return modelMapper.map(fotoProduto, FotoProdutoDTO.class);
	}
}
