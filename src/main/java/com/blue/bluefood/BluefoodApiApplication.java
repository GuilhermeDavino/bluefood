package com.blue.bluefood;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.blue.bluefood.domain.model.Cozinha;
import com.blue.bluefood.domain.model.Restaurante;
import com.blue.bluefood.domain.repository.CozinhaRepository;
import com.blue.bluefood.domain.repository.RestauranteRepository;

@SpringBootApplication
public class BluefoodApiApplication implements CommandLineRunner {
	
	@Autowired
	private CozinhaRepository cadastro;
	
	@Autowired
	private RestauranteRepository restaurante;
	
	public static void main(String[] args) {
		SpringApplication.run(BluefoodApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		List<Cozinha> cozinhas = cadastro.todas();
		for(Cozinha cozinha: cozinhas) {
			System.out.println(cozinha.getNome());
		}
		
		Cozinha cozinha1 = new Cozinha();
		Cozinha cozinha2 = new Cozinha();
		
		cozinha1.setNome("Brasileira");
		cozinha2.setNome("Americana");
		
		cozinha1 = cadastro.salvar(cozinha1);
		cozinha2 = cadastro.salvar(cozinha2);
		
		System.out.printf("%d - %s%n", cozinha1.getId(), cozinha1.getNome());
		System.out.printf("%d - %s%n", cozinha2.getId(), cozinha2.getNome());
		
		Cozinha cozinha3 = cadastro.buscarPorId(1L);
		
		System.out.printf("busca: %d - %s%n", cozinha3.getId(), cozinha3.getNome());
		
		// cadastro.remover(cozinha3);
		// cozinha3 = cadastro.porId(1L);
		 
		System.out.printf("busca: %d - %s%n", cozinha3.getId(), cozinha3.getNome());
		
		Restaurante restaurante1 = restaurante.buscarPorId(3L);
		
		System.out.printf("Restaurante: %s - Frente: %.1f - Cozinha: %s %n", restaurante1.getNome(), restaurante1.getTaxaFrete(), restaurante1.getCozinha().getNome());
	}

	

}
