package com.blue.bluefood.api.model;

import javax.validation.constraints.NotNull;

import com.blue.bluefood.domain.model.Cozinha;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class CozinhaIdInput {
	
	@NotNull
	private Long id;
	
	public CozinhaIdInput(Long id) {
		this.id = id;
	}
	
	public CozinhaIdInput(Cozinha cozinha) {
		this.id = cozinha.getId();
	}
}
