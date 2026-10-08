package com.blue.bluefood.api.model;

import java.math.BigDecimal;

import com.blue.bluefood.api.model.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteDTO {
	@JsonView(RestauranteView.Resumo.class)
	private Long id;
	@JsonView(value = {RestauranteView.Resumo.class, RestauranteView.ApenasNome.class})
	private String nome;
	@JsonView(RestauranteView.Resumo.class)
	private BigDecimal taxaFrete;
	@JsonView(RestauranteView.Resumo.class)
	private CozinhaDTO cozinha;
	private Boolean ativo;
	private Boolean aberto;
	private EnderecoDTO endereco;
	
	
	
}
