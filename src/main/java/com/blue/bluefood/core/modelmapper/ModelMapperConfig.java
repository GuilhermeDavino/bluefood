package com.blue.bluefood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.blue.bluefood.api.model.EnderecoDTO;
import com.blue.bluefood.domain.model.Endereco;

@Configuration
public class ModelMapperConfig {
	
	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();
		var enderecoToEnderecoDTO = modelMapper.createTypeMap(Endereco.class, EnderecoDTO.class);
		enderecoToEnderecoDTO.<String>addMapping(enderecoSrc -> 
		enderecoSrc.getCidade().getEstado().getNome(), (enderecoDest, value) -> enderecoDest.getCidade().setEstado(value));
		return modelMapper;
	}
}
