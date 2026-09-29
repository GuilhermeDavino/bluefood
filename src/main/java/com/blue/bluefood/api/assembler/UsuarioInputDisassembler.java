package com.blue.bluefood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blue.bluefood.api.model.UsuarioInputDTO;
import com.blue.bluefood.domain.model.Usuario;

@Component
public class UsuarioInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;

	public Usuario toDomainObject(UsuarioInputDTO usuarioInput) {
		return modelMapper.map(usuarioInput, Usuario.class);
	}
	
	public void copyToDomainObject(UsuarioInputDTO usuarioInput,
			Usuario usuario) {
		modelMapper.map(usuarioInput, usuario);
	}
}
