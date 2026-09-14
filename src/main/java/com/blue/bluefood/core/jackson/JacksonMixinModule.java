package com.blue.bluefood.core.jackson;

import org.springframework.stereotype.Component;

import com.blue.bluefood.api.model.mixin.CozinhaMixin;
import com.blue.bluefood.api.model.mixin.RestauranteMixin;
import com.blue.bluefood.domain.model.Cozinha;
import com.blue.bluefood.domain.model.Restaurante;
import com.fasterxml.jackson.databind.module.SimpleModule;

@Component
public class JacksonMixinModule extends SimpleModule {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JacksonMixinModule() {
		setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
		setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
	}

}
