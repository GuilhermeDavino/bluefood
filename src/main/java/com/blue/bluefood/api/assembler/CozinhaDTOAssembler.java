package com.blue.bluefood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blue.bluefood.api.model.CozinhaDTO;
import com.blue.bluefood.domain.model.Cozinha;

@Component
public class CozinhaDTOAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public CozinhaDTO toCozinhaDTO(Cozinha cozinha) {
		return modelMapper.map(cozinha, CozinhaDTO.class);
	}
	
	public List<CozinhaDTO> toCollectionDTO(List<Cozinha> cozinhas) {
		return cozinhas.stream().
				map(x -> toCozinhaDTO(x)).collect(Collectors.toList());
	}
}
