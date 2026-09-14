package com.blue.bluefood.api.model.mixin;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

import com.blue.bluefood.domain.model.Cozinha;
import com.blue.bluefood.domain.model.Endereco;
import com.blue.bluefood.domain.model.FormaPagamento;
import com.blue.bluefood.domain.model.Produto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class RestauranteMixin {
	@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "nome"}, allowGetters = true)
	private Cozinha cozinha;
	
	@JsonIgnore
	private Endereco endereco;
	
	@JsonIgnore
	private Set<FormaPagamento> formasPagamento;
	
	@JsonIgnore
	private List<Produto> produtos;
	
	@JsonIgnore
	private OffsetDateTime dataCadastro;
	
	@JsonIgnore
	private OffsetDateTime dataAtualizacao;
}
