package com.blue.bluefood.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.blue.bluefood.domain.model.Restaurante;
@Repository
public interface RestauranteRepository extends CustomJpaRepository<Restaurante, Long>,
RestauranteRepositoryQueries, JpaSpecificationExecutor<Restaurante> {
	
	List<Restaurante> todos();
	
	Restaurante buscarPorId(Long id);
	
	Restaurante salvar(Restaurante restaurante);
	
	void remover(Long id);
	
	List<Restaurante> find(String nome, BigDecimal TaxaFreteInicial, BigDecimal TaxaFreteFinal);
}
