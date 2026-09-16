package com.blue.bluefood.api.model;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoDTO {
	
	private Long id;
	
	@NotBlank
	private String nome;
}
