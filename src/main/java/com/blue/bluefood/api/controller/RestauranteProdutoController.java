package com.blue.bluefood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blue.bluefood.api.assembler.ProdutoDTOAssembler;
import com.blue.bluefood.api.model.ProdutoDTO;
import com.blue.bluefood.domain.service.RestauranteService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {
	
	@Autowired
	private RestauranteService restauranteService;
	
	@Autowired
	private ProdutoDTOAssembler produtoAssembler;
	
	@GetMapping
	public ResponseEntity<List<ProdutoDTO>> listar(@PathVariable Long restauranteId) {
		var restaurante = restauranteService.buscarOuFalhar(restauranteId);
		var produtos = restaurante.getProdutos();
		var produtosDTO = produtoAssembler.toCollectionDTO(produtos);
		return ResponseEntity.ok(produtosDTO);
	}
	
	@GetMapping("/{produtoId}")
	public ResponseEntity<ProdutoDTO> buscarPorId(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		var produto = restauranteService.buscarProdutoPorId(restauranteId, produtoId);
		var produtoDTO = produtoAssembler.toProdutoDTO(produto);
		return ResponseEntity.ok(produtoDTO);
	}
	
	@PutMapping("/{produtoId}")
	public ResponseEntity<Void> adicionarProdutoAoRestaurante(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		restauranteService.adicionarProduto(restauranteId, produtoId);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{produtoId}")
	public ResponseEntity<Void> removerProdutoAoRestaurante(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		restauranteService.removerProduto(restauranteId, produtoId);
		return ResponseEntity.noContent().build();
	}
}
