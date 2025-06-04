package com.blue.bluefood.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.blue.bluefood.domain.model.Restaurante;
@Repository
public interface RestauranteRepository extends CustomJpaRepository<Restaurante, Long>,
RestauranteRepositoryQueries, JpaSpecificationExecutor<Restaurante> {
	
	@Query("from Restaurante r join r.cozinha join fetch r.formasPagamento")
	List<Restaurante> todos();
	
	Restaurante buscarPorId(Long id);
	
	Restaurante salvar(Restaurante restaurante);
	
	void remover(Long id);
	
	List<Restaurante> find(String nome, BigDecimal TaxaFreteInicial, BigDecimal TaxaFreteFinal);
}
