package com.blue.bluefood.api.model;

import java.math.BigDecimal;

import com.blue.bluefood.domain.model.Cozinha;
import com.blue.bluefood.domain.model.Restaurante;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteDTO {
	
	private Long id;
	private String nome;
	private BigDecimal taxaFrete;
	private CozinhaDTO cozinha;
	
	public RestauranteDTO(Restaurante restaurante) {
		setId(restaurante.getId());
		setNome(restaurante.getNome());
		setTaxaFrete(restaurante.getTaxaFrete());
		CozinhaDTO cozinhaDTO =  cozinhaModelToDTO(restaurante.getCozinha());
		setCozinha(cozinhaDTO);
	}
	
	private CozinhaDTO cozinhaModelToDTO(Cozinha cozinha) {
		return new CozinhaDTO(cozinha);
	}
	
	
	
}
