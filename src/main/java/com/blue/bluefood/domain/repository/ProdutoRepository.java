package com.blue.bluefood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.blue.bluefood.domain.model.FotoProduto;
import com.blue.bluefood.domain.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>, ProdutoRepositoryQueries {
	
	@Query(value = "from Produto where restaurante.id = :restaurante and id = :produto")
	Optional<Produto> findById(@Param("restaurante") Long restaurenteId, @Param("produto") Long produtoId);
	
	@Query("from Produto p where p.ativo = true and p.restaurante.id = :restaurante")
	List<Produto> buscarTodosProdutosAtivos(@Param("restaurante") Long restaurenteId);
	
	@Query("select f from FotoProduto f join f.produto p "
			+ "where p.restaurante.id = :restauranteId and f.produto.id = :produtoId")
	Optional<FotoProduto> findFotoById(Long produtoId, Long restauranteId);
}
