package com.blue.bluefood.api.model;

import com.blue.bluefood.domain.model.Cozinha;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CozinhaDTO {
	
	private Long id;
	private String nome;
	
	public CozinhaDTO(Cozinha cozinha) {
		setId(cozinha.getId());
		setNome(cozinha.getNome());
	}
	
}
