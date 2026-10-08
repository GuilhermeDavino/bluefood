package com.blue.bluefood.api.model;

import javax.validation.constraints.NotBlank;

import com.blue.bluefood.api.model.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CozinhaDTO {
	@JsonView(RestauranteView.Resumo.class)
	private Long id;
	
	@JsonView(RestauranteView.Resumo.class)
	@NotBlank
	private String nome;
	
}
