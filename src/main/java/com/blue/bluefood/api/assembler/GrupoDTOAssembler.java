package com.blue.bluefood.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blue.bluefood.api.model.GrupoDTO;
import com.blue.bluefood.domain.model.Grupo;

@Component
public class GrupoDTOAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public GrupoDTO toGrupoDTO(Grupo grupo) {
		return modelMapper.map(grupo, GrupoDTO.class);
	}
	
	public List<GrupoDTO> toCollectionDTO(Collection<Grupo> grupos) {
		return grupos.stream().map(x -> toGrupoDTO(x))
				.collect(Collectors.toList());
	}
}
