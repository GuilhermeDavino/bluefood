package com.blue.bluefood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blue.bluefood.api.model.FormaPagamentoDTO;
import com.blue.bluefood.domain.model.Cidade;
import com.blue.bluefood.domain.model.FormaPagamento;

@Component
public class FormaPagamentoInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public FormaPagamento toDomainObject(FormaPagamentoDTO formaPagamentoDTO) {
		return modelMapper.map(formaPagamentoDTO, FormaPagamento.class);
	}
	
	public void copyToDomainObject(FormaPagamento cidadeInput, Cidade cidade) {
		modelMapper.map(cidadeInput, cidade);
	}
}
