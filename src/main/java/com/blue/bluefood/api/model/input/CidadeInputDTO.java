package com.blue.bluefood.api.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.blue.bluefood.core.validation.Groups;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeInputDTO {
	
	@NotNull(groups = {Groups.CidadeId.class})
	private Long id;
	
	@NotBlank
	private String nome;
	
	@Valid
	@NotNull
	private EstadoIdInput estado;
	 
	
}
