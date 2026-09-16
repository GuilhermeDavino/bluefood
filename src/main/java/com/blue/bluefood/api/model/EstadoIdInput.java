package com.blue.bluefood.api.model;

import javax.validation.constraints.NotNull;

import com.blue.bluefood.domain.model.Estado;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoIdInput {
	@NotNull
	private Long id;
	
	public EstadoIdInput(Long id) {
		this.id = id;
	}
	
	public EstadoIdInput(Estado estado) {
		this.id = estado.getId();
	}
}
