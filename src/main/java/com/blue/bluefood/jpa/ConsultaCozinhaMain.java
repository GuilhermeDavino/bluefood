package com.blue.bluefood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.blue.bluefood.BluefoodApiApplication;
import com.blue.bluefood.domain.model.Cozinha;
import com.blue.bluefood.domain.repository.CozinhaRepository;
@SpringBootApplication
public class ConsultaCozinhaMain {
	
	public static void main(String[] args) { 
		ApplicationContext applicationContext = new SpringApplicationBuilder(BluefoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		CozinhaRepository cadastroCozinha = applicationContext.getBean(CozinhaRepository.class);
		List<Cozinha> cozinhas = cadastroCozinha.todas();
		
		for(Cozinha cozinha : cozinhas) {
			System.out.println(cozinha.getNome());
		}
	}
}
