package com.blue.bluefood.core.squiggly;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bohnman.squiggly.Squiggly;
import com.github.bohnman.squiggly.web.RequestSquigglyContextProvider;
import com.github.bohnman.squiggly.web.SquigglyRequestFilter;

@Configuration
public class SquigglyConfig {
	
	@Bean
	public FilterRegistrationBean<SquigglyRequestFilter> squigglyRequestFilter(ObjectMapper objectMapper) {
		Squiggly.init(objectMapper, new RequestSquigglyContextProvider("campos", null));
		var filterRegistraion =  new FilterRegistrationBean<SquigglyRequestFilter>();
		
		var urlPatterns = Arrays.asList("/pedidos/*", "/restaurantes/*");
		
		filterRegistraion.setFilter(new SquigglyRequestFilter());
		filterRegistraion.setOrder(1);
		filterRegistraion.setUrlPatterns(urlPatterns);
		
		return filterRegistraion;
	}
}
