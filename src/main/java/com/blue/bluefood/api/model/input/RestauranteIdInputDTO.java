package com.blue.bluefood.api.model.input;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteIdInputDTO {
	
	@NotNull
	private Long id;
}
