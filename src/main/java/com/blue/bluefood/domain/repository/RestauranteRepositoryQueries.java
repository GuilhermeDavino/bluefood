package com.blue.bluefood.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import com.blue.bluefood.domain.model.Restaurante;

public interface RestauranteRepositoryQueries {
	
	List<Restaurante> find(String nome, BigDecimal TaxaFreteInicial, BigDecimal TaxaFreteFinal);
	List<Restaurante> listarComFreteGratis(String nome);
}
