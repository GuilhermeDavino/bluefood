package com.blue.bluefood;

import static org.assertj.core.api.Assertions.assertThat;

import javax.validation.ConstraintViolationException;

import org.flywaydb.core.Flyway;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.blue.bluefood.domain.exception.EntidadeEmUsoException;
import com.blue.bluefood.domain.exception.EntidadeNaoEncontradaException;
import com.blue.bluefood.domain.model.Cozinha;
import com.blue.bluefood.domain.service.CozinhaService;
import com.blue.bluefood.util.DatabaseCleaner;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("/aplication-test.properties")
public class CadastroCozinhaIT {

	@LocalServerPort
	private int port;
	
	@Autowired
	private Flyway flyway;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Test
	public void contextLoads() {
	}
	
	@Autowired
	private CozinhaService cozinhaService;
	
	@Before
	public void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cozinhas";
		databaseCleaner.clearTables();
	}
	
	@Test
	public void shouldReturnOkWhenRetrieveCozinhas() {
		RestAssured
		.given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void shouldReturn4CozinhaWhenRetrieveCozinhas() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		
		RestAssured
		.given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.body("", Matchers.hasSize(6))
			.body("nome", Matchers.hasItems("Tailandesa", "Chilena"))
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void shouldCreateAndReturnCreatedWhenPost() {
		
		RestAssured.given()
			.body("{\"nome\": \"Chinesa\"}")
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
			
	}
	
	@Test
	public void shouldRegisterCozinha() { 
		Cozinha cozinha = new Cozinha();
		cozinha.setNome("Lebron");
		
		cozinha = cozinhaService.salvar(cozinha);
		
		assertThat(cozinha).isNotNull();
		assertThat(cozinha.getId()).isNotNull();
	}
	
	@Test(expected = ConstraintViolationException.class)
	public void shouldThrowExceptionWhenNameNull() {
		Cozinha cozinha = new Cozinha();
		
		cozinha.setNome(null);
		
		cozinha = cozinhaService.salvar(cozinha);
	}
	
	@Test(expected = EntidadeEmUsoException.class)
	public void shouldThrowEntidadeEmUsoExceptionWhenCozinhaIsUsed() {
		Cozinha cozinha = cozinhaService.buscarOuFalhar(1L);
		cozinhaService.remover(cozinha.getId());
	}
	
	@Test(expected = EntidadeNaoEncontradaException.class)
	public void shouldThrowEntidadeNaoEncontradaExceptionWhenRetrieveCozinhaNonExists() {
		var cozinha = cozinhaService.buscarOuFalhar(1000L);
		cozinha.getId();
	}
	
	private void prepararDados() {
		var cozinha1 =  new Cozinha();
		cozinha1.setNome("Tailandesa");
		cozinhaService.salvar(cozinha1);
		var cozinha2 = new Cozinha();
		cozinha2.setNome("Indiana");
		cozinhaService.salvar(cozinha2);
	}

}