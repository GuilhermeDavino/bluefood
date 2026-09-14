package com.blue.bluefood.api.model.mixin;

import java.util.List;

import com.blue.bluefood.domain.model.Restaurante;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class CozinhaMixin {
	
	@JsonIgnore
	private List<Restaurante> restaurantes;
}
