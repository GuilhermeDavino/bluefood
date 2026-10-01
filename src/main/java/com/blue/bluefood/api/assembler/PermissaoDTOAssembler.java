package com.blue.bluefood.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blue.bluefood.api.model.PermissaoDTO;
import com.blue.bluefood.domain.model.Permissao;

@Component
public class PermissaoDTOAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public PermissaoDTO toPermissaoDTO(Permissao permissao) {
		return modelMapper.map(permissao, PermissaoDTO.class);
	}
	
	public List<PermissaoDTO> toCollectionDTO(Collection<Permissao> permissoes) {
		return permissoes.stream().map(x -> toPermissaoDTO(x)).collect(Collectors.toList());
	}
}
