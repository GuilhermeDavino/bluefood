package com.blue.bluefood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blue.bluefood.api.model.CidadeDTO;
import com.blue.bluefood.domain.model.Cidade;

@Component
public class CidadeDTOAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public CidadeDTO toCidadeDTO(Cidade cidade) {
		return modelMapper.map(cidade, CidadeDTO.class);
	}
	
	public List<CidadeDTO> toCollectionDTO(List<Cidade> cidades) {
		return cidades.stream().map(x -> toCidadeDTO(x))
				.collect(Collectors.toList());
	}
}
