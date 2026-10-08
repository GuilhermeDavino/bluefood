package com.blue.bluefood.api.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.blue.bluefood.api.assembler.ProdutoDTOAssembler;
import com.blue.bluefood.api.assembler.ProdutoInputDisassembler;
import com.blue.bluefood.api.model.ProdutoDTO;
import com.blue.bluefood.api.model.input.ProdutoInputDTO;
import com.blue.bluefood.domain.model.Produto;
import com.blue.bluefood.domain.service.ProdutoService;
import com.blue.bluefood.domain.service.RestauranteService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {
	
	@Autowired
	private RestauranteService restauranteService;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ProdutoDTOAssembler produtoAssembler;
	
	@Autowired
	private ProdutoInputDisassembler produtoDisassembler;
	
	@GetMapping
	public ResponseEntity<List<ProdutoDTO>> listar(@PathVariable Long restauranteId, @RequestParam Boolean incluirInativos) {
		List<Produto> produtos = null;
		if(incluirInativos) {
			produtos = restauranteService.buscarTodosProdutos(restauranteId);
		} else {
			produtos = restauranteService.buscarTodosProdutosAtivos(restauranteId);
		}
		var produtosDTO = produtoAssembler.toCollectionDTO(produtos);
		return ResponseEntity.ok(produtosDTO);
	}
	
	@GetMapping("/{produtoId}")
	public ResponseEntity<ProdutoDTO> buscarPorId(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		var produto = restauranteService.buscarProdutoPorId(restauranteId, produtoId);
		var produtoDTO = produtoAssembler.toProdutoDTO(produto);
		return ResponseEntity.ok(produtoDTO);
	}
	
	@PostMapping
	public ResponseEntity<ProdutoDTO> adicionarProdutoAoRestaurante(@PathVariable Long restauranteId, @RequestBody @Valid ProdutoInputDTO produtoInput) {
		var produto = produtoDisassembler.toDomainObject(produtoInput);
		produto = produtoService.adicionar(restauranteId, produto);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(produto.getId())
				.toUri();
		
		var produtoDTO = produtoAssembler.toProdutoDTO(produto);
		return ResponseEntity.created(uri).body(produtoDTO);
	}
	
	@PutMapping("/{produtoId}")
	public ResponseEntity<ProdutoDTO> atualizarProduto(@PathVariable Long restauranteId, @PathVariable Long produtoId, @RequestBody @Valid ProdutoInputDTO produtoInput) {
		var produto = produtoService.buscarProdutoPorRestaurante(produtoId, restauranteId);
		produtoDisassembler.copyToDomainObject(produtoInput, produto);
		produto = produtoService.atualizar(produto);
		var produtoDTO = produtoAssembler.toProdutoDTO(produto);
		return ResponseEntity.ok(produtoDTO);
	}
}
