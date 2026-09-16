package com.blue.bluefood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blue.bluefood.api.model.FormaPagamentoDTO;
import com.blue.bluefood.domain.model.FormaPagamento;

@Component
public class FormaPagamentoDTOAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	

	public FormaPagamentoDTO toFormaPagamentoDTO(FormaPagamento formaPagamento) {
		return modelMapper.map(formaPagamento, FormaPagamentoDTO.class);
	}
	
	public List<FormaPagamentoDTO> toCollectionDTO(List<FormaPagamento> formasDePagamento) {
		return formasDePagamento.stream().map(x -> toFormaPagamentoDTO(x)).collect(Collectors.toList());
	}
}
