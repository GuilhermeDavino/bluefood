package com.blue.bluefood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.blue.bluefood.api.model.EnderecoDTO;
import com.blue.bluefood.api.model.input.ItemPedidoInputDTO;
import com.blue.bluefood.domain.model.Endereco;
import com.blue.bluefood.domain.model.ItemPedido;

@Configuration
public class ModelMapperConfig {
	
	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();
		
		modelMapper.createTypeMap(ItemPedidoInputDTO.class, ItemPedido.class)
		.addMappings(mapper -> mapper.skip(ItemPedido::setId));
		
		var enderecoToEnderecoDTO = modelMapper.createTypeMap(Endereco.class, EnderecoDTO.class);
		
		enderecoToEnderecoDTO.<String>addMapping(enderecoSrc -> 
		enderecoSrc.getCidade().getEstado().getNome(), (enderecoDest, value) -> enderecoDest.getCidade().setEstado(value));
		
		
		return modelMapper;
	}
}
