package com.blue.bluefood.api.model;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeDTO {
	
	@NotNull
	private Long id;
	@NotBlank
	private String nome;
	@NotNull
	@Valid
	private EstadoDTO estado;
	
}
