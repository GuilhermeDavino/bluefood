package com.blue.bluefood.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.blue.bluefood.domain.model.Pedido;

@Repository
public interface PedidoRepository extends CustomJpaRepository<Pedido, Long>, 
	JpaSpecificationExecutor<Pedido>{
	
	@Query("from Pedido where codigo = :codigo")
	Optional<Pedido> findByCodigo(String codigo);
	
	@Query(value = "from Pedido p join fetch p.cliente join fetch p.restaurante r join fetch r.cozinha",
			countQuery = "select count(p) from Pedido p")
	Page<Pedido> findAll(Pageable pageble);
}
