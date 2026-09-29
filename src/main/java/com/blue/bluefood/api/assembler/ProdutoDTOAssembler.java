package com.blue.bluefood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blue.bluefood.api.model.ProdutoDTO;
import com.blue.bluefood.domain.model.Produto;

@Component
public class ProdutoDTOAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public ProdutoDTO toProdutoDTO(Produto produto) {
		return modelMapper.map(produto, ProdutoDTO.class);
	}
	
	public List<ProdutoDTO> toCollectionDTO(List<Produto> produtos) {
		return produtos.stream().map(x -> toProdutoDTO(x)).collect(Collectors.toList());
	}
}
