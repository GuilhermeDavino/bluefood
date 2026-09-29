package com.blue.bluefood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blue.bluefood.api.model.UsuarioDTO;
import com.blue.bluefood.domain.model.Usuario;

@Component
public class UsuarioDTOAssembler {
	@Autowired
	private ModelMapper modelMapper;
	
	public UsuarioDTO toUsuarioDTO(Usuario usuario) {
		return modelMapper.map(usuario, UsuarioDTO.class);
	}
	
	public List<UsuarioDTO> toCollectionDTO(List<Usuario> usuarios) {
		return usuarios.stream()
				.map(x -> toUsuarioDTO(x))
				.collect(Collectors.toList());
	}
}
